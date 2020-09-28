package com.example.cvproject1;

public class User {
    String userName;
    String userMail;
    String userPass;
    String userPhone;
    Boolean phoneVerified;

    public User() {
    }

    public User(String userData) {
        String[] cuttingNow = userData.split("\\'\\$\\'");
        userName = cuttingNow[0];
        userMail = cuttingNow[1];
        userPass = cuttingNow[2];
        userPhone = cuttingNow[3];
        phoneVerified = Boolean.parseBoolean(cuttingNow[4]);
    }

    public User(String userName, String userMail, String userPass, String userPhone, Boolean phoneVerified) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPass = userPass;
        this.userPhone = userPhone;
        this.phoneVerified = phoneVerified;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
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
                userPhone + "'$'" +
                phoneVerified;
    }
}
