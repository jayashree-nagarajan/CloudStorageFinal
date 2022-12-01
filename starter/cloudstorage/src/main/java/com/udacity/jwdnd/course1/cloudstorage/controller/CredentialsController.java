package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {
    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;



    @PostMapping("/save")
    public  String addCredentials(Authentication authentication,
                                  @ModelAttribute("credentials") Credentials credentials,
                                  Model model) throws Exception{
        //System.out.println("postMapping of Credentials");
        ModelAndView modelAndView = new ModelAndView();
        User user= userService.getUser(authentication.getName());
        int result = 0;
        String message;
        result= credentialsService.addCredentials(credentials,user.getUserId());
        if(result < 1){
            System.out.println("postMapping inside add credentials : Not saved");
            message = "error";
            model.addAttribute("error",message);
        }
        else{
            //System.out.println("postMapping inside add credentials");
            //System.out.println("postMapping inside add credentials result :"+result);
            model.addAttribute("credentials",credentials);
            message = "success";
            model.addAttribute("success",message);
        }
        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredentials(@RequestParam("credentialId") Integer credentialId, Model model) {
        System.out.println("Inside delete postMapping credential");
        if (credentialId > 0) {
            credentialsService.deleteCredentials(credentialId);
            model.addAttribute("success","success");
            return "result";
        } else {
            model.addAttribute("error", "error");
            return "result";
        }
    }

}
