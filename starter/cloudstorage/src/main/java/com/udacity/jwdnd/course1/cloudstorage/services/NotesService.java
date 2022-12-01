package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotesService {
    @Autowired
    private NotesMapper notesMapper;

    public List<Notes> getAllNotes(int userid) throws Exception {
        List<Notes> notes = notesMapper.getNotesByUserId(userid);
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }
    public List<Notes> getAllNotes2() throws Exception {
        List<Notes> notes = notesMapper.getAllNotes2();
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }

    public Notes getNotesByNotedId(int noteId) throws Exception {
        Notes notes = notesMapper.getNotesByNotedId(noteId);
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }

    public int addNotes(Notes notes, int userid) throws Exception {
        Notes note = new Notes();
        note.setNoteTitle(notes.getNoteTitle());
        note.setNoteDescription(notes.getNoteDescription());
        if(notes.getNoteId() == null){
            note.setNoteId(0);
            System.out.println("Inside NotesService addNotes");
            return notesMapper.addNotes(note, userid);
        }
        else {
            Notes updatedNotes = getNotesByNotedId(notes.getNoteId());
            return notesMapper.updateNote(notes);
        }
        //return -1;
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }
}
