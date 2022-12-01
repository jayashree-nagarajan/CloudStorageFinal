package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private FilesService filesService;
    @Autowired
    private CredentialsService credentialsService;

    @GetMapping()

    public String homeView(Authentication authentication,

                           @ModelAttribute("multipartFile") MultipartFile multipartFile,
                           Model model) throws Exception {
        String usr = authentication.getName();
        System.out.println("Home Getmapping");
        User user = userService.getUser(usr);//(User)authentication.getPrincipal();
        System.out.println("User id"+ user.getUserId());
        if(multipartFile!= null){
            System.out.println("File Name"+ multipartFile.getOriginalFilename());
        }
        List<Notes> notesByUserId = notesService.getAllNotes(user.getUserId());
        List<Files> filesList = filesService.getAllFiles(user.getUserId());
        List<Credentials> credentials = credentialsService.getCredentialsByUserId(user.getUserId());

        model.addAttribute("notes",notesByUserId);
        model.addAttribute("files",filesList);
        model.addAttribute("credentials",credentials);
        model.addAttribute("credentialsService",credentialsService);
        return "home";
    }


}
