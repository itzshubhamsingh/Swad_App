package com.example.swadapp.Models;

public class User {
    String username, email, password, profilepic, userId, phone, postal;




    public User(String username, String email, String password, String profilepic, String userId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilepic = profilepic;
        this.userId = userId;
    }



    public  User(String username, String email, String password, String userId, String postal, String phone){
        this.username = username;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.postal = postal;
        this.phone = phone;
    }

    public User(){}

    // Signup constructor

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
