package com.senior.project.genealogy.view.fragment.notification;

import com.senior.project.genealogy.response.Notification;

import java.util.List;

public interface NotificationPresenter {
    void getNotifications(String token);
    void getNotificationsSuccess(List<Notification> notificationList);
    void getNotificationsFalse();
}
