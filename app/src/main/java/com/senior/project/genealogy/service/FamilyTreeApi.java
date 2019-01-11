package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.response.People;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface FamilyTreeApi {

    @POST("/api/people")
    Call<PeopleResponse> createPeople(@Body People people, @Header("Authorisation") String token);

    @PUT("/api/people")
    Call<CodeResponse> updatePeople(@Body People people, @Header("Authorisation") String token);

    @HTTP(method = "DELETE", path = "/api/people",  hasBody = true)
    Call<CodeResponse> deletePeople(@Body int peopleId, @Header("Authorisation") String token);

    @POST("/api/people/branch")
    Call<PeopleResponse> getPeopleByBranchId(@Body int branchId, @Header("Authorisation") String token);

}
