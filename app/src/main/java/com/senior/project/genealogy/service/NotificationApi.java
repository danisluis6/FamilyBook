package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NotificationApi {

    @GET("/api/notification/user")
    Call<NotificationResponse> getListOfNotifications(@Header("Authorisation") String token);

}
