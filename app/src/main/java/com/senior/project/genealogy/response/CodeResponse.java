package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeResponse {

    @SerializedName("error")
    @Expose
    private Message error;

    public CodeResponse(Message error) {
        this.error = error;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }
}
