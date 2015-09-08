package com.rainbow.lightnote.model;

/**
 * Created by weijuner on 2015/9/6.
 */
public class Note {
    int noteId;//笔记号
    int userId;//用户号
    String category;//笔记分类
    String title;//笔记标题
    String content;//笔记内容
    String time;//笔记时间

    public Note(int userId,String category, String title, String content, String time) {
        this.userId = userId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}