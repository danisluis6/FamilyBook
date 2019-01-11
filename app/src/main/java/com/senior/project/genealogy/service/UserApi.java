package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.response.EventResponse;
import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {

    @POST("login")
    Call<LoginResponse> login(@Body User user);

    @POST("register")
    Call<LoginResponse> register(@Body User user);

    @GET("api/user")
    Call<UserResponse> getProfile(@Header("Authorisation") String token);

    @PUT("api/user")
    Call<UserResponse> updateProfile(@Header("Authorisation") String token, @Body User user);
}
