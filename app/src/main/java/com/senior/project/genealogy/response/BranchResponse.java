package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("branchList")
    @Expose
    private List<Branch> branchList;

    public BranchResponse(Message error, List<Branch> branchList) {
        this.error = error;
        this.branchList = branchList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }
}
