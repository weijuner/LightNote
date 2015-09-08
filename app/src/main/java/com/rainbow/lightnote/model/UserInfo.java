package com.rainbow.lightnote.model;

/**
 * Created by weijuner on 2015/9/6.
 */
public class UserInfo {
    int userId;
    String userName;
    String password;
    String birthday;
    String sex;
    String job;
    String telephone;
    String email;
    String microblog;//微博
    String headPortrait;//个人头像

    public UserInfo(int userId, String userName, String password, String birthday, String sex, String job, String telephone, String email, String microblog, String headPortrait) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.job = job;
        this.telephone = telephone;
        this.email = email;
        this.microblog = microblog;
        this.headPortrait = headPortrait;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMicroblog() {
        return microblog;
    }

    public void setMicroblog(String microblog) {
        this.microblog = microblog;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }
}
