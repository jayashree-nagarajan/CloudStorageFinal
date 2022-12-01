package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS where userid = #{userId}")
    List<Credentials> getCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> getAllCREDENTIALS2();

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{credentials.url},#{credentials.userName},#{credentials.key},#{credentials.password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentials.credentialId")
    Integer addCredentials(Credentials credentials,int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credentials getCredentialById(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, key = #{key} , password = #{password} , userId = #{userId} WHERE credentialId = #{credentialId}")
    Integer updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredentials(Integer credentialId);
}
