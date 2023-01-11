package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private FileSystemStorageService storageService;


    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, FileSystemStorageService storageService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.storageService = storageService;
    }

    @GetMapping("/home")
    public String homeView(Authentication auth, Model model) {
        try {
            String username = auth.getName();
            int userid = userService.getUser(username).getUserid();
            ArrayList<Note> notes = noteService.getNotes(userid);
            model.addAttribute("notes", notes);
            ArrayList<Credential> crds = credentialService.getCredentials(userid);
            model.addAttribute("credentials", crds);
            model.addAttribute("files", storageService.loadAll(userid));
            return "home";
        } catch (Exception e){
            return "login";
        }
    }

    public Model prepareModel(Authentication auth, Model model){
        String username = auth.getName();
        int userid = userService.getUser(username).getUserid();
        ArrayList<Note> notes = noteService.getNotes(userid);
        model.addAttribute("notes", notes);
        ArrayList<Credential> crds = credentialService.getCredentials(userid);
        model.addAttribute("credentials", crds);
        model.addAttribute("files", storageService.loadAll(userid));
        model.addAttribute("message", null);
        return model;
    }

    @RequestMapping("/")
    public String errorMapping(Authentication auth) {
        return "signup";
    }
}