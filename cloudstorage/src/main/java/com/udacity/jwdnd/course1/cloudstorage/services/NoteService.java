package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note){
        int noteid = noteMapper.insert(note);
        note.setNoteid(noteid);
        return noteid;
    }

    public void deleteNote(int noteId){
        noteMapper.delete(noteId);
    }

    public void updateNote(int noteId, String title, String description){
        noteMapper.updateNote(noteId, title, description);
    }

    public Note getNote(int noteId){
        return noteMapper.getNote(noteId);
    }

    public ArrayList<Note> getNotes(int userid){
        return noteMapper.getNotes(userid);
    }

}
