package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.MemberApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailMemberRequestBranchModelImpl implements DetailMemberRequestBranchModel {
    private DetailMemberRequestBranchFragmentPresenter mDetailMemberRequestBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailMemberRequestBranchModelImpl(DetailMemberRequestBranchFragmentPresenter detailMemberRequestBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailMemberRequestBranchFragmentPresenter = detailMemberRequestBranchFragmentPresenter;
    }

    @Override
    public void getRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token) {
        Call<UserResponse> call = mApplicationApi.getClient().create(MemberApi.class).getMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                int code = Integer.parseInt(userResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchSuccess(userResponse.getUserList());
                } else {
                    mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchFalse();
            }
        });
    }

    @Override
    public void acceptRequestMemberOfBranch(UserBranchPermission userBranchPermission, final User user, String token, final int position) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(MemberApi.class).acceptRequestMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberRequestBranchFragmentPresenter.acceptRequestMemberOfBranchSuccess(position, user);
                } else {
                    mDetailMemberRequestBranchFragmentPresenter.acceptRequestMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mDetailMemberRequestBranchFragmentPresenter.acceptRequestMemberOfBranchFalse();
            }
        });
    }

    @Override
    public void declineRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token, final int position) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(MemberApi.class).declineRequestMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberRequestBranchFragmentPresenter.declineRequestMemberOfBranchSuccess(position);
                } else {
                    mDetailMemberRequestBranchFragmentPresenter.declineRequestMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mDetailMemberRequestBranchFragmentPresenter.declineRequestMemberOfBranchFalse();
            }
        });
    }

}
