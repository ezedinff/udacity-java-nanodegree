package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView() {
        if (userService.isUsernameAvailable("yasmine"))
            userService.createUser(new User(null, "yasmine", "mohamed", "mohamed", "mohamed", "mohamed"));
        return "signup";
    }

    @PostMapping
    public String signUpUser(User user, Model model) {
        String errorMsg = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            errorMsg = "Sorry, this username is not available!";

        }
        if (errorMsg == null) {
            int userId = userService.createUser(user);
            if (userId < 0) {
                errorMsg = "There was an error signing you up. Please try again.";
            }
        }
        if (errorMsg == null) {
            model.addAttribute("signupSuccess", true);
            model.addAttribute("message", "You registered successfully!");
            return "login";
        }
        model.addAttribute("signupError", errorMsg);
        return "signup";


    }
}

