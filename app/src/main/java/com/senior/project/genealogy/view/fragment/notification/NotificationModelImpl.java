package com.senior.project.genealogy.view.fragment.notification;

import com.senior.project.genealogy.response.NotificationResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.service.NotificationApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationModelImpl implements NotificationModel {

    private NotificationPresenter  mNotificationPresenter;
    private ApplicationApi mApplicationApi;

    public NotificationModelImpl(NotificationPresenter notificationPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mNotificationPresenter = notificationPresenter;
    }

    @Override
    public void getNotifications(String token) {
        Call<NotificationResponse> call = mApplicationApi.getClient().create(NotificationApi.class).getListOfNotifications(token);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                NotificationResponse notificationResponse = response.body();
                int code = Integer.parseInt(notificationResponse.getError().getCode());

                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mNotificationPresenter.getNotificationsSuccess(notificationResponse.getNotificationList());
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mNotificationPresenter.getNotificationsFalse();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                mNotificationPresenter.getNotificationsFalse();
            }
        });
    }
}
