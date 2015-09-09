package com.rainbow.lightnote.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rainbow.lightnote.db.DatabaseHelper;
import com.rainbow.lightnote.model.Note;

/**
 * Created by weijuner on 2015/9/7.
 */
public class DbNoteManager {
    DatabaseHelper helper = null;
    public DbNoteManager(Context cxt) {
        helper = new DatabaseHelper(cxt);
    }
    public void insertNote(Note note) {
        String sql = "insert into Note(userid,categoryid,notetitle,noteContent,notetime" +
                ")values(?,?,?,?,?)";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql, new Object[] { note.getUserId(),
                note.getCategory(),
                note.getTitle(),
                note.getContent(),
                note.getTime()});
    }
}
