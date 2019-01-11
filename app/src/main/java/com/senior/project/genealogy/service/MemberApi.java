package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MemberApi {

    @POST("api/member")
    Call<UserResponse> getMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @HTTP(method = "DELETE", path = "api/member",  hasBody = true)
    Call<CodeResponse> declineRequestMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @PUT("api/member")
    Call<CodeResponse> acceptRequestMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @PUT("api/member/role")
    Call<CodeResponse> changeRoleMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @POST("api/member/branch")
    Call<CodeResponse> joinBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @HTTP(method = "DELETE", path = "api/member/branch",  hasBody = true)
    Call<CodeResponse> outBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

}
