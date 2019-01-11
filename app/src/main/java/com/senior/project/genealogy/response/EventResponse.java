package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse {

    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("eventList")
    @Expose
    private List<Event> eventList;

    public EventResponse() {
    }

    public EventResponse(Message error, List<Event> eventList) {
        this.error = error;
        this.eventList = eventList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<User> userList) {
        this.eventList = eventList;
    }
}
