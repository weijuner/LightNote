package com.rainbow.lightnote.engin;

import com.rainbow.lightnote.model.Note;
import com.rainbow.lightnote.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijuner on 2015/9/9.
 */
public class NoteManager {
    static List<Note> notes = new ArrayList<Note>();
    public void addNote(Note note){
        notes.add(note);
    }
    public List<Note> getNote(){
        return   MainActivity.notes;
    }
    public void deleteNote(int position){
        notes.remove(position);
    }
    public List<Note> getNotes(){
        return notes;
    }
}
