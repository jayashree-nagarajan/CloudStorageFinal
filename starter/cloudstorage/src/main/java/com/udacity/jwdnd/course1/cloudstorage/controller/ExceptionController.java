package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class ExceptionController implements ErrorController {


    @GetMapping("/error")
    public String customError(Model model,@ModelAttribute("errorMessage") String errorMessage) {
        System.out.println("Inside error getMapping : "+errorMessage);
        model.addAttribute("errorMessage","Invalid url...");
        return "result";
    }



    @Override
    public String getErrorPath() {
        return "result";
    }
}

