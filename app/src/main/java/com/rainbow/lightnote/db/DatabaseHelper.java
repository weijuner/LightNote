package com.rainbow.lightnote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weijuner on 2015/9/7.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME ="lightnote.db";
    private final static int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[0]
                + "([userId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "[userName] VARCHAR(20) NOT NULL," +
                "[password] VARCHAR(20) NOT NULL);");
       db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[1]
                + "([id] INTEGER,[username] VARCHAR," +
               "[password] VARCHAR," +
               "[nickname] VARCHAR(15)," +
               "[birthday] DATE," +
               "[sex] VARCHAR(6)," +
               "[job] VARCHAR(12)," +
               "[telephone] VARCHAR(20)," +
               "[email] VARCHAR," +
               "[microblog] VARCHAR," +
               "[headPortrait] VARCHAR," +
               "[userid] INTEGER,FOREIGN KEY[userid] REFERENCES User([userid])"
       );
/*       db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[2]+"(" +
                "categoryid int auto_increment," +
                "categoryname varchar(20) unique," +
                "primary key categoryid);"
        );
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[3]+"(" +
                "labelid int auto_increment," +
                "labelname varchar(20) unique," +
                "primary key labelid);"
        );
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[4]+"(" +
                "photoid int auto_increment," +
                "noteid int," +
                "photopath varchar(20) unique," +
                "primary key photoid," +
                "foregin key noteid references Note(noteid));"
        );
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[5]+"(" +
                        "recordid int auto_increment," +
                        "noteid int,recordpath varchar(20) unique," +
                        "primary key recordid," +
                        "foregin key noteid references Note(noteid)" +
                        ");"
        );
        //笔记表
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[6]+"(" +
                        "noteid int auto_increment," +
                        "userid int,categoryid int," +
                        "notetitle varchar(20) not null," +
                        "noteContent varchar(200) not null," +
                        "notetime Date," +
                        "primary key noteid," +
                        "foregin key userid references User(userid)," +
                        " key categoryid references Category(categoryid));"
        );
        //标签-笔记表NoteLabel
        db.execSQL("CREATE TABLE " + TableNames.TABLE_NAME[7]+"(" +
                        "noteid int," +
                        "labelid int," +
                        "primary key(noteid,labelid)," +
                        "foregin key noteid references Note(noteid)," +
                        "forehin key labelid references Label(labelid));"
        );*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}