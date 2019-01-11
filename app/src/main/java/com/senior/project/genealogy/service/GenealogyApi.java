package com.senior.project.genealogy.service;


import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.response.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface GenealogyApi {

    @POST("/api/genealogy")
    Call<GenealogyResponse> createGenealogy(@Body Genealogy genealogy, @Header("Authorisation") String token);

    @PUT("/api/genealogy")
    Call<CodeResponse> updateGenealogy(@Body Genealogy genealogy, @Header("Authorisation") String token);

    @HTTP(method = "DELETE", path = "/api/genealogy",  hasBody = true)
    Call<CodeResponse> deleteGenealogy(@Body int genealogyId, @Header("Authorisation") String token);

    @GET("/api/genealogy/user")
    Call<GenealogyResponse> getGenealogiesByUserName(@Header("Authorisation") String token);

}
