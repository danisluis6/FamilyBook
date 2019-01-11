package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.PeopleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FamilyRelationApi {

    @POST("/api/familyRelation")
    Call<PeopleResponse> getFamilyRelation(@Body int peopleId, @Header("Authorisation") String token);

}
