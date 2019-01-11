package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    public Message getError() {
        return error;
    }

    public void setError(Message message) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
