package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class People implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("branchId")
    @Expose
    private Integer branchId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("deathDay")
    @Expose
    private String deathDay;

    @SerializedName("gender")
    @Expose
    private Integer gender;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("degree")
    @Expose
    private String degree;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("lifeIndex")
    @Expose
    private Integer lifeIndex;

    @SerializedName("parentId")
    @Expose
    private Integer parentId;

    @SerializedName("partnerId")
    @Expose
    private Integer partnerId;

    @SerializedName("appellation")
    @Expose
    private String appellation;

    public People() {
    }

    public People(String name, String birthday, Integer gender, String image) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
    }

    public People(Integer id, String name, Integer gender, Integer parentId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(String deathDay) {
        this.deathDay = deathDay;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(Integer lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}



















