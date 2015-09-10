package com.rainbow.lightnote.model;

import java.util.List;

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
    List<Lable> lables;
    List<String> photo;
    List<String> record;

    public Note() {
    }

    public Note(int userId,String category, String title, String content, String time) {
        this.userId = userId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public List<String> getRecord() {
        return record;
    }

    public void setRecord(List<String> record) {
        this.record = record;
    }

    public List<Lable> getLables() {
        return lables;
    }

    public void setLables(List<Lable> lables) {
        this.lables = lables;
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
    public String[] getLableArray(){
        String[] lableArray = new String[lables.size()];
        for(int i = 0;i<lables.size();i++){
            lableArray[i] = lables.get(i).getLableName();
        }
        return lableArray;
    }
}
