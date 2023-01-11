package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class NotesController {
    private UserService userService;
    private NoteService noteService;
    private HomeController homeController;

    public NotesController(UserService userService, NoteService noteService, HomeController homeController) {
        this.userService = userService;
        this.noteService = noteService;
        this.homeController = homeController;
    }

    @PostMapping("/notes")
    public String addOrUpdateNote(Authentication auth, Note note, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        if(note.getNotedescription() != null) {
            String desc = note.getNotedescription().replace('\n', ',');
            note.setNotedescription(desc);
        }
        if(note.getNoteid()!=null){
            noteService.updateNote(note.getNoteid(), note.getNotetitle(), note.getNotedescription());
        }
        else {
            note.setUserid(userid);
            noteService.addNote(note);
        }
        ArrayList<Note> notes = noteService.getNotes(userid);
        model.addAttribute("notes", notes);
        homeController.prepareModel(auth, model);
        model.addAttribute("message","Note added/updated successfully!");
        return "home";
    }

    @PostMapping("/deletenote")
    public String deleteNote(Authentication auth, Note note, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        System.out.println(note);
        noteService.deleteNote(note.getNoteid());
        ArrayList<Note> notes = noteService.getNotes(userid);
        model.addAttribute("notes", notes);
        homeController.prepareModel(auth, model);
        model.addAttribute("message","Note deleted successfully!");
        return "home";
    }
}