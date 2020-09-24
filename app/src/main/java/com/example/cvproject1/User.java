package com.example.cvproject1;

import android.content.Context;

public class User {
    String userName;
    String userMail;
    String userPass;
    String userPhone;

    public User() {
    }

    public User(String userData) {
        String[] cuttingNow = userData.split("\\'\\$\\'");
        userName = cuttingNow[0];
        userMail = cuttingNow[1];
        userPass = cuttingNow[2];
        userPhone = cuttingNow[3];
    }

    public User(String userName, String userMail, String userPass, String userPhone) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPass = userPass;
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    @Override
    public String toString() {
        return  userName + "'$'" +
                userMail + "'$'" +
                userPass + "'$'" +
                userPhone;
    }
}
