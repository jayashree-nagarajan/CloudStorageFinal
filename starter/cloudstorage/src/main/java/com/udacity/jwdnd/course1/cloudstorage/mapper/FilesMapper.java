package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM FILES where userid = #{userId}")
    List<Files> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES where fileId = #{fileId}")
    Files getFilesByFileId(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize,filedata,userid) VALUES (#{files.fileName},#{files.contentType},#{files.fileSize},#{files.fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "files.fileId")
    Integer addFiles(Files files,int userId);

    @Select("SELECT * FROM FILES where filename = #{fileName} AND userid = #{userId}")
    Files getFilesByFileName(String fileName,int userId);

    @Delete("DELETE FROM Files WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);

}
