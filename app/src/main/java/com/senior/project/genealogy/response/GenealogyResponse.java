package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenealogyResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("genealogyList")
    @Expose
    private List<Genealogy> genealogyList;

    public GenealogyResponse(Message error, List<Genealogy> genealogyList) {
        this.error = error;
        this.genealogyList = genealogyList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Genealogy> getGenealogyList() {
        return genealogyList;
    }

    public void setGenealogyList(List<Genealogy> genealogyList) {
        this.genealogyList = genealogyList;
    }
}
