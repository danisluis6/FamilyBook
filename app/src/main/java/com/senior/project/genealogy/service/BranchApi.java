package com.senior.project.genealogy.service;


import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.BranchResponse;
import com.senior.project.genealogy.response.CodeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BranchApi {

    @POST("/api/branch")
    Call<BranchResponse> createBranch(@Body Branch branch, @Header("Authorisation") String token);

    @PUT("/api/branch")
    Call<CodeResponse> updateBranch(@Body Branch branch, @Header("Authorisation") String token);

    @HTTP(method = "DELETE", path = "/api/branch",  hasBody = true)
    Call<CodeResponse> deleteBranch(@Body int branchId, @Header("Authorisation") String token);

    @POST("/api/branch/genealogy")
    Call<BranchResponse> getBranchesByGenealogyId(@Body int genealogyId, @Header("Authorisation") String token);

    @POST("/api/branch/id")
    Call<BranchResponse> getBranchesByBranchId(@Body int branchId, @Header("Authorisation") String token);
}
