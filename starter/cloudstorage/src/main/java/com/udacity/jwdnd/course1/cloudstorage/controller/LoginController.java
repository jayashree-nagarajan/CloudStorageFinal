package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String loginView(Model model,@ModelAttribute("signupSuccess") String signupSuccess){
       if(signupSuccess.equalsIgnoreCase("true")){
           model.addAttribute("signupSuccess","true");
       }
       else{
           model.addAttribute("signupSuccess","false");
       }
       return "login";
    }
}
