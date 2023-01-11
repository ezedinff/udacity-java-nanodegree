package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    ArrayList<Note> getNotes(int userid);

    @Select("Select * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(int noteid);

    @Insert("INSERT INTO NOTES (userid, notetitle, notedescription) VALUES(#{userid}, #{notetitle}, #{notedescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(int noteid);

    @Update("UPDATE NOTES SET notedescription = #{notedescription}, notetitle = #{notetitle} WHERE noteid = #{noteid}")
    void updateNote(int noteid, String notetitle, String notedescription);

}