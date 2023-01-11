package com.udacity.jwdnd.course1.cloudstorage.controller;


import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileSystemStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.coyote.Request;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;

@Controller
public class FileUploadController {

    private final FileSystemStorageService storageService;
    private UserService userService;
    private HomeController homeController;
    private final int maxFileSize = 128000;

    @Autowired
    public FileUploadController(FileSystemStorageService storageService, UserService userService, HomeController homeController) {
        this.storageService = storageService;
        this.userService = userService;
        this.homeController = homeController;
    }


    @GetMapping("/files/{fileId:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(Authentication auth, @PathVariable int fileId, Model model) throws SQLException {

        File file = storageService.load(fileId);
        byte[] data = file.getFiledata();
        homeController.prepareModel(auth, model);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model, Authentication auth) throws IOException, SQLException {
        try {
            String username = auth.getName();
            User user = userService.getUser(username);
            int userid = user.getUserid();
            if (!file.isEmpty()) {
                if (storageService.isFileNameAvailable(userid, file.getOriginalFilename())) {
                    System.out.println(file.getSize() + " bytes");
                    if (file.getSize() <= maxFileSize) {
                        storageService.databaseStore(userid, file);
                        homeController.prepareModel(auth, model);
                        model.addAttribute("message",
                                "You successfully uploaded " + file.getOriginalFilename() + "!");
                    } else {

                        homeController.prepareModel(auth, model);
                        model.addAttribute("message",
                                "You are trying to upload a file that exceeds the maximum file size!");
                    }
                } else {
                    homeController.prepareModel(auth, model);
                    model.addAttribute("message",
                            "You already uploaded a file with this name!");
                }
            } else {
                homeController.prepareModel(auth, model);
                model.addAttribute("message",
                        "You did not choose a file!");
            }
        }
        catch (Exception e) {
            homeController.prepareModel(auth, model);
            model.addAttribute("message",
                    "Failed to store file, try again!");

        }
        return "home";
    }

    @PostMapping("/deletefile")
    public String deleteFile(File file, Model model, Authentication auth) {
        try {
            storageService.delete(file.getFileId());
            homeController.prepareModel(auth, model);
            model.addAttribute("message", "File deleted successfully");
            return "home";
        } catch (Exception e) {
            homeController.prepareModel(auth, model);
            model.addAttribute("message", "Problem occured during file deletion, please try again!");

            return "home";
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}