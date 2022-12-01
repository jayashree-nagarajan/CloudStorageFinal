package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    @Autowired
    private CredentialsMapper credentialsMapper;
    @Autowired
    private HashService hashService;
    @Autowired
    private EncryptionService encryptionService;


    public List<Credentials> getCredentialsByUserId(int userid) throws Exception {
        List<Credentials> credentials = credentialsMapper.getCredentialsByUserId(userid);
        Credentials c1;
        if (credentials == null) {
            throw new Exception();
        }
        return credentials;
    }


    public Credentials getCredentialById(int credentialId) throws Exception {
        Credentials credentials = credentialsMapper.getCredentialById(credentialId);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials;
    }

    public int addCredentials(Credentials credentials, int userid) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedSalt = Base64.getEncoder().encodeToString(key);
        String hashedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedSalt);
        if(credentials.getCredentialId() == null){

            return credentialsMapper.addCredentials(new Credentials(null,credentials.getUrl(),credentials.getUserName(),encodedSalt,hashedPassword,userid), userid);
        }
        else{
            credentials.setKey(encodedSalt);
            credentials.setPassword(hashedPassword);
            credentials.setUserId(userid);
            System.out.println("updateCredentials +"+credentials.getCredentialId()+"-"+credentials.getUrl()+"-"+credentials.getUserName()+"-"+credentials.getPassword()+"-"+credentials.getKey()+"-"+credentials.getUserId());
            return credentialsMapper.updateCredentials(credentials);
        }
    }

    public void deleteCredentials(Integer credentialId) {
        credentialsMapper.deleteCredentials(credentialId);
    }

    public String decryptPassword(String password,String key) {

        String decryptedPassword = encryptionService.decryptValue(password,key);
        return decryptedPassword;
    }

}
