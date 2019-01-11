package com.senior.project.genealogy.view.fragment.profile.ShowProfile;

import com.senior.project.genealogy.response.UserResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModelImpl implements ProfileModel {
    private ProfileFragmentPresenter mProfileFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public ProfileModelImpl(ProfileFragmentPresenter profileFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mProfileFragmentPresenter = profileFragmentPresenter;
    }

    @Override
    public void getProfile(String token) {
        Call<UserResponse> call = mApplicationApi.getClient().create(UserApi.class).getProfile(token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                int code = Integer.parseInt(userResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mProfileFragmentPresenter.getProfileSuccess(userResponse.getUserList().get(0));
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mProfileFragmentPresenter.getProfileFalse();
                        break;
                    default:
                        mProfileFragmentPresenter.getProfileFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mProfileFragmentPresenter.getProfileFalse();
            }
        });
    }

}
