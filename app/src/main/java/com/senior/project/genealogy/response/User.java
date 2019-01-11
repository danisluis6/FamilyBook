package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("role")
    @Expose
    private Integer role;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("mail")
    @Expose
    private String mail;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("gender")
    @Expose
    private boolean gender;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public User() {
    }

    public User(String username, String password, String fullname, String mail, String address, String avatar, String birthday, boolean gender) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.mail = mail;
        this.address = address;
        this.avatar = avatar;
        this.birthday = birthday;
        this.gender = gender;
    }

    public User(String username, String password, String fullname, String deviceId) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.deviceId = deviceId;
    }

    public User(String username, String password, String deviceId) {
        this.username = username;
        this.password = password;
        this.deviceId = deviceId;
    }

    public User(String username, String password, Integer role, String fullname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
