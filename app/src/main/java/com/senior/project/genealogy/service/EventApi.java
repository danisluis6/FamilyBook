package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.response.EventResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EventApi {

    @POST("/api/event")
    Call<EventResponse> pushEvent(@Body Event event, @Header("Authorisation") String token);

    @POST("/api/event/user")
    Call<EventResponse> getCreatedEvent(@Body Integer branchId, @Header("Authorisation") String token);

    @POST("/api/event/branch")
    Call<EventResponse> getEvents(@Body Integer branchId, @Header("Authorisation") String token);

}
