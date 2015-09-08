package com.rainbow.lightnote.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rainbow.lightnote.db.DatabaseHelper;
import com.rainbow.lightnote.model.User;

/**
 * Created by weijuner on 2015/9/7.
 */
public class UserManager {
    DatabaseHelper helper = null;

    public UserManager(Context cxt) {
        helper = new DatabaseHelper(cxt);
    }

    public void insertUser(User user) {
        String sql = "insert into User(userName,password) values(?,?)";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql, new Object[]{user.getUserName(),
                user.getPassWord()});
    }
}
