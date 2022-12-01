package com.udacity.jwdnd.course1.cloudstorage.Exceptions;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping({"/notes","/files","credentials"})
public class FileExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ModelAndView modelAndView = new ModelAndView();
        String message = "File size is too large.";
        System.out.println("Inside FileExceptionController"+message);
        modelAndView.addObject("errorMessage",message);
        modelAndView.setViewName("result");
        return modelAndView;
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception e){
        ModelAndView modelAndView = new ModelAndView();
        String message = e.getMessage();
        System.out.println("Inside handleAllExceptions"+message);
        modelAndView.addObject("exceptionMessage",message);
        modelAndView.addObject("invalidMessage","Invalid url...");
        modelAndView.setViewName("result");
        return modelAndView;
    }


}
