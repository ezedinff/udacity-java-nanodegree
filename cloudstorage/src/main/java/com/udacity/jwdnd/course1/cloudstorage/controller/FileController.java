package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class FileController {
//    private UserMapper userMapper;
//    private NoteMapper noteMapper;

//    public FileController(UserMapper userMapper, NoteMapper noteMapper) {
//        this.userMapper = userMapper;
//        this.noteMapper = noteMapper;
//    }

    //TODO add logic
    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        InputStream fis = file.getInputStream();
        return null;
    }

}