package com.senior.project.genealogy.view.fragment.notification;

import com.senior.project.genealogy.response.Notification;

import java.util.List;

public class NotificationPresenterImpl implements NotificationPresenter {

    private NotificationModel mNotificationModel;
    private NotificationView mNotificationView;

    public NotificationPresenterImpl(NotificationView notificationView) {
        mNotificationView = notificationView;
        mNotificationModel = new NotificationModelImpl(this);
    }


    @Override
    public void getNotifications(String token) {
        mNotificationView.showProgressDialog();
        mNotificationModel.getNotifications(token);
    }

    @Override
    public void getNotificationsSuccess(List<Notification> notificationList) {
        if (mNotificationView != null) {
            mNotificationView.closeProgressDialog();
            mNotificationView.showListNotifications(notificationList);
        }
    }

    @Override
    public void getNotificationsFalse() {
        mNotificationView.closeProgressDialog();
    }
}
