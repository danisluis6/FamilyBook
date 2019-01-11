package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("peopleList")
    @Expose
    private List<People> peopleList;

    public PeopleResponse(Message error, List<People> peopleList) {
        this.error = error;
        this.peopleList = peopleList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }
}
