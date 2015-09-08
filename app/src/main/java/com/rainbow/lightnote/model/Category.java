package com.rainbow.lightnote.model;

/**
 * Created by weijuner on 2015/9/6.
 * 笔记分类实体
 */
public class Category {
    int categoryid;
    String categoryname;

    public Category(int categoryid, String categoryname) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
