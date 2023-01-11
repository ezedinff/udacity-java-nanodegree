package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserMapper userMapper;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public int addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        int id = credentialMapper.insert(new Credential(credential.getUserid(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword));
        return id;
    }

    public void deleteCredential(int credentialid){
        credentialMapper.delete(credentialid);
    }

    public void updateCredential(int credentialid, String url, String username, String password){
        Credential crd = credentialMapper.getCredential(credentialid);
        String encryptedPassword = encryptionService.encryptValue(password, crd.getKey());
        credentialMapper.updateCredential(credentialid, url, username, encryptedPassword);
    }

    public Credential getCredential(int credentialid){
        Credential crd = credentialMapper.getCredential(credentialid);
        String decryptedPassword = encryptionService.decryptValue(crd.getPassword(), crd.getKey());
        crd.setUnencryptedPassword(decryptedPassword);
        return crd;
    }

    //Not sure of modification in foreach
    public ArrayList<Credential> getCredentials(int userid){
        ArrayList<Credential> crds = credentialMapper.getCredentials(userid);
        for (Credential crd: crds){
            String decryptedPassword = encryptionService.decryptValue(crd.getPassword(), crd.getKey());
            crd.setUnencryptedPassword(decryptedPassword);
        }
        return crds;
    }

}