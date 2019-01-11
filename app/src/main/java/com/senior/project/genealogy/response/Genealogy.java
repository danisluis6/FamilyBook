package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Genealogy implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("history")
    @Expose
    private String history;

    @SerializedName("owner")
    @Expose
    private String owner;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("branch")
    @Expose
    private Integer branch;

    @SerializedName("role")
    @Expose
    private Integer role;

    @SerializedName("branchList")
    @Expose
    private List<Branch> branchList;

    public Genealogy(Integer id, String name, String history) {
        this.id = id;
        this.name = name;
        this.history = history;
    }

    public Genealogy(Integer id, String name, String history, String owner, Date date, Integer branch, Integer role) {
        this.id = id;
        this.name = name;
        this.history = history;
        this.owner = owner;
        this.date = date;
        this.branch = branch;
        this.role = role;
    }

    public Genealogy(String name, String history) {
        this.name = name;
        this.history = history;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name;
    }
}
