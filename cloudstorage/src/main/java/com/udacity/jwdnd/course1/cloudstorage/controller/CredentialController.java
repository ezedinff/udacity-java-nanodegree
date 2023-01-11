package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;
    private HomeController homeController;

    public CredentialController(UserService userService, CredentialService credentialService, HomeController homeController) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.homeController = homeController;
    }

    @PostMapping("/credentials")
    public String addOrUpdateCredential(Authentication auth, Credential credential, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        if(credential.getCredentialid()!=null){
            credentialService.updateCredential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), credential.getPassword());
        }
        else {
            credential.setUserid(userid);
            credentialService.addCredential(credential);
        }
        ArrayList<Credential> crds = credentialService.getCredentials(userid);
        model.addAttribute("credentials", crds);
        homeController.prepareModel(auth, model);
        model.addAttribute("message","Credential added/updated successfully!");
        return "home";
    }

    @PostMapping("/deletecredential")
    public String deleteNote(Authentication auth, Credential credential, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        credentialService.deleteCredential(credential.getCredentialid());
        ArrayList<Credential> crds = credentialService.getCredentials(userid);
        model.addAttribute("credentials", crds);
        homeController.prepareModel(auth, model);
        model.addAttribute("message","Credential deleted successfully!");
        return "home";
    }
}