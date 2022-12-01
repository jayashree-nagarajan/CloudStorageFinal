package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class FilesService {
    @Autowired
    private FilesMapper filesMapper;

    public List<Files> getAllFiles(int userid) throws Exception {
        List<Files> files = filesMapper.getFilesByUserId(userid);
        if (files == null) {
            throw new Exception();
        }
        return files;
    }

    public Files getFilesByFileId(int fileId) throws Exception {
        Files files = filesMapper.getFilesByFileId(fileId);
        if (files == null) {
            throw new Exception();
        }
        return files;
    }

    public Files getFilesByFileName(String fileName,int userId) throws Exception {
        Files files = filesMapper.getFilesByFileName(fileName,userId);
        if (files == null) {
            throw new Exception();
        }
        return files;
    }

    public int addFiles(MultipartFile multipartFile, int userid) throws Exception {
        Files newFile = new Files();
        newFile.setUserId(userid);
        newFile.setFileName(multipartFile.getOriginalFilename());
        newFile.setFileData(multipartFile.getBytes());
        newFile.setContentType(multipartFile.getContentType());
        newFile.setFileSize(String.valueOf(multipartFile.getSize()));
        if(newFile.getFileName().isEmpty()){
            return -1;
        }
        else{
            if(Long.parseLong(newFile.getFileSize()) > 5242880){
                return -3;
            }
            Files chkFileName = filesMapper.getFilesByFileName(newFile.getFileName(),userid);
            if(chkFileName != null){
                return -2;
            }
        }
        return filesMapper.addFiles(newFile,userid);
    }

    public void deleteFile(Integer fileId) {
        filesMapper.deleteFile(fileId);
    }


}
