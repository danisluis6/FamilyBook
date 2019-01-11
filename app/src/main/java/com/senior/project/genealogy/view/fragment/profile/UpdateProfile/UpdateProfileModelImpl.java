package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileModelImpl implements UpdateProfileModel {
    private UpdateProfileFragmentPresenter mUpdateProfileFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public UpdateProfileModelImpl(UpdateProfileFragmentPresenter updateProfileFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mUpdateProfileFragmentPresenter = updateProfileFragmentPresenter;
    }

    @Override
    public void updateProfile(String token, User user) {
        Call<UserResponse> call = mApplicationApi.getClient().create(UserApi.class).updateProfile(token, user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                int code = Integer.parseInt(userResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mUpdateProfileFragmentPresenter.updateProfileSuccess(userResponse.getUserList().get(0));
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mUpdateProfileFragmentPresenter.updateProfileFalse();
                        break;
                    default:
                        mUpdateProfileFragmentPresenter.updateProfileFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mUpdateProfileFragmentPresenter.updateProfileFalse();
            }
        });
    }

}
