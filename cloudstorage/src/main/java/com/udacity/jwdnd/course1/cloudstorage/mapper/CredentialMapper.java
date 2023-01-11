package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    ArrayList<Credential> getCredentials(int userid);

    @Select("Select * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(int credentialid);

    @Insert("INSERT INTO CREDENTIALS (userid, url, username, key, password) VALUES(#{userid}, #{url}, #{username}, #{key}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(int credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    void updateCredential(int credentialid, String url, String username, String password);

}