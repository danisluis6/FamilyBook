package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserBranchPermission implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("branch_id")
    @Expose
    private int branch_id;

    @SerializedName("branch_permission_id")
    @Expose
    private int branch_permission_id;

    public UserBranchPermission(int branch_id) {
        this.branch_id = branch_id;
    }

    public UserBranchPermission(boolean status, int branch_id) {
        this.status = status;
        this.branch_id = branch_id;
    }

    public UserBranchPermission(String username, int branch_id) {
        this.username = username;
        this.branch_id = branch_id;
    }

    public UserBranchPermission(String username, int branch_id, int branch_permission_id) {
        this.username = username;
        this.branch_id = branch_id;
        this.branch_permission_id = branch_permission_id;
    }

    public UserBranchPermission(int branch_id, int branch_permission_id) {
        this.branch_id = branch_id;
        this.branch_permission_id = branch_permission_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getBranch_permission_id() {
        return branch_permission_id;
    }

    public void setBranch_permission_id(int branch_permission_id) {
        this.branch_permission_id = branch_permission_id;
    }
}
