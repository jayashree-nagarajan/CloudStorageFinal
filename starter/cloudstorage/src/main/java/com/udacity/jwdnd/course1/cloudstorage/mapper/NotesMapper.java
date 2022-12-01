package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES where userid = #{userId}")
    List<Notes> getNotesByUserId(Integer userId);
    @Select("SELECT * FROM NOTES")
    List<Notes> getAllNotes2();

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notes.noteTitle},#{notes.noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "notes.noteId")
    Integer addNotes(Notes notes,int userId);


    @Select("SELECT * FROM Notes WHERE noteId = #{noteId}")
    Notes getNotesByNotedId(Integer noteId);

    @Update("UPDATE Notes SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}, userId = #{userId} WHERE noteId = #{noteId}")
    Integer updateNote(Notes notes);

    @Delete("DELETE FROM Notes WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
}
