package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Branch implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("member")
    @Expose
    private Integer member;

    @SerializedName("role")
    @Expose
    private Integer role;

    @SerializedName("genealogyId")
    @Expose
    private Integer genealogyId;

    public Branch(Integer id, String name, String description, Date date, Integer member, Integer role) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.member = member;
        this.role = role;
    }

    public Branch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Branch(String name, String description, Integer genealogyId) {
        this.name = name;
        this.description = description;
        this.genealogyId = genealogyId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
