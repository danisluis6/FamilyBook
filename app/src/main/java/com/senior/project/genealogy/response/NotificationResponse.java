package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("notificationList")
    @Expose
    private List<Notification> notificationList;

    public NotificationResponse(Message error, List<Notification> notificationList) {
        this.error = error;
        this.notificationList = notificationList;
    }

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
