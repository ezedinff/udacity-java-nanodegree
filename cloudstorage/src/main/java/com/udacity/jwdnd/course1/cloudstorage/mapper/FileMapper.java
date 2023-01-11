package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import java.util.ArrayList;


@Mapper
public interface FileMapper {
    @Select("SELECT fileid, filename FROM FILES WHERE userid = #{userid}")
    ArrayList<File> getFiles(int userId);


    @Select("Select fileId, userid, filename, contenttype, filesize, filedata FROM FILES WHERE fileId = #{fileId}")
    File getFile(int fileId);

    @Insert("INSERT INTO FILES (userid, filename, contenttype, filesize, filedata) VALUES(#{userid}, #{filename}, #{contenttype}, #{filesize}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(int fileId);

}