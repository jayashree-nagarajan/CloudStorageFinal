package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String createNote(Authentication authentication,
                             @ModelAttribute("notes") Notes notes,
                             Model model) throws Exception {

        String message = null;
        int result = 0;
        List<Notes> notesList;
        User user= userService.getUser(authentication.getName());
        Notes newNotes = new Notes();
        newNotes.setNoteId(notes.getNoteId());
        newNotes.setNoteDescription(notes.getNoteDescription());
        newNotes.setNoteTitle(notes.getNoteTitle());
        newNotes.setUserId(user.getUserId());
        result =notesService.addNotes(newNotes,user.getUserId());
        notesList = notesService.getAllNotes2();
        if (result <1){
            message = "error";
            model.addAttribute("notes",notesList);
            model.addAttribute("error",message);
            return "result/error";
        }
        else{
            message = "success";
            model.addAttribute("notes",notesList);
            model.addAttribute("success",message);
            return "result";
        }

    }

    @GetMapping("/delete")
    public String deleteNotes(@RequestParam("noteId") Integer noteId,Model model) {
        System.out.println("Inside delete getMapping");
        if (noteId > 0) {
            notesService.deleteNote(noteId);
            model.addAttribute("success","success");
            return "result";
        }
        else{
            model.addAttribute("error","error");
            return "result/error";
        }

    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId,Model model) {
        System.out.println("Inside delete postMapping");
        if (noteId > 0) {
            notesService.deleteNote(noteId);
            return "result";
        }
        else{
            model.addAttribute("error","error");
            return "result/error";
        }

    }
}

