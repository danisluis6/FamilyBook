package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("userList")
    @Expose
    private List<User> userList;

    public UserResponse() {
    }

    public UserResponse(Message error, List<User> userList) {
        this.error = error;
        this.userList = userList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
