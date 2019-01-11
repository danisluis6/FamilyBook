package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.MemberApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailMemberBranchModelImpl implements DetailMemberBranchModel {
    private DetailMemberBranchFragmentPresenter mDetailMemberBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailMemberBranchModelImpl(DetailMemberBranchFragmentPresenter detailMemberBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailMemberBranchFragmentPresenter = detailMemberBranchFragmentPresenter;
    }

    @Override
    public void getMemberOfBranch(UserBranchPermission userBranchPermission, String token) {
        Call<UserResponse> call = mApplicationApi.getClient().create(MemberApi.class).getMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                int code = Integer.parseInt(userResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberBranchFragmentPresenter.getMemberOfBranchSuccess(userResponse.getUserList());
                } else {
                    mDetailMemberBranchFragmentPresenter.getMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mDetailMemberBranchFragmentPresenter.getMemberOfBranchFalse();
            }
        });
    }

    @Override
    public void changeRoleMemberOfBranch(final UserBranchPermission userBranchPermission, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(MemberApi.class).changeRoleMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberBranchFragmentPresenter.changeRoleMemberOfBranchSuccess(userBranchPermission);
                } else {
                    mDetailMemberBranchFragmentPresenter.changeRoleMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mDetailMemberBranchFragmentPresenter.changeRoleMemberOfBranchFalse();
            }
        });
    }
}
