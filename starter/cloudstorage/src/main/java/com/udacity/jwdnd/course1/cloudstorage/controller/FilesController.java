package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/files")
public class FilesController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String uploadFile(Authentication authentication,
                             @ModelAttribute("multipartFile") MultipartFile multipartFile,
                             Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        User user= userService.getUser(authentication.getName());
        int result = 0;
        String message;

            result = filesService.addFiles(multipartFile, user.getUserId());
            if(result == -1){
                model.addAttribute("errorMessage","Please select a file to upload");
                return "result";
            }
            else if(result == -2){
                model.addAttribute("errorMessage","This file already exists!!");
                return "result";
            }
            else if(result == -3){
                model.addAttribute("errorMessage","This file size is too big to upload!!");
                return "result";
            }
            else{
                System.out.println("postMapping inside add files");
                message = "success";
                model.addAttribute("multipartFile",multipartFile);
                model.addAttribute("success",message);
                return "result";
            }


    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileId") Integer fileId, Model model) {

        if (fileId > 0) {
            filesService.deleteFile(fileId);
            model.addAttribute("success","success");
            return "result";
        }
        else{
            model.addAttribute("error","error");
            return "result/error";
        }

    }
    @GetMapping("/view")
    public ResponseEntity<InputStreamResource> viewFile(@RequestParam("fileId") Integer fileId) throws Exception {
        Files files = filesService.getFilesByFileId(fileId);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(files.getFileData()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:fileName=" + files.getFileName())
                .contentType(MediaType.parseMediaType(files.getContentType()))
                .body(inputStreamResource);
    }

}
