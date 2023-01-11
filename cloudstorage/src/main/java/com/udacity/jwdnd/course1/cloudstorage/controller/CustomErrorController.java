package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req){
//        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
