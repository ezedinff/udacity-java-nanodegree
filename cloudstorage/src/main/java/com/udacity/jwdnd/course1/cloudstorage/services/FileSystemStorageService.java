package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.udacity.jwdnd.course1.cloudstorage.config.StorageProperties;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService {

    private final Path rootLocation;
    private UserService userService;
    private FileMapper fileMapper;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, UserService userService, FileMapper fileMapper) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public boolean isFileNameAvailable(int userid, String filename) {

        ArrayList<File> files = fileMapper.getFiles(userid);
        for (File file : files)
            if (file.getFilename().equals(filename))
                return false;
        return true;
    }

    public void databaseStore(int userid, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            String contenttype = file.getContentType();
            String size = "" + file.getSize();
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            File oFile = new File();
            oFile.setUserid(userid);
            oFile.setFilename(filename);
            oFile.setContenttype(contenttype);
            oFile.setFilesize(size);
            oFile.setFiledata(bytes);
            fileMapper.insert(oFile);

        } catch (Exception e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }


    public ArrayList<File> loadAll(int userid) {
        return fileMapper.getFiles(userid);
    }


    public File load(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public void delete(int fileId) {
        fileMapper.delete(fileId);
    }


}