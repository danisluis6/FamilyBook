package com.senior.project.genealogy.view.activity.login;


import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModelImpl implements LoginModel {
    private LoginPresenter mLoginPresenter;
    private ApplicationApi mApplicationApi;

    public LoginModelImpl(LoginPresenter loginPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mLoginPresenter = loginPresenter;
    }

    @Override
    public void login(final User user) {
        Call<LoginResponse> call = mApplicationApi.getClient().create(UserApi.class).login(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                int code = Integer.parseInt(loginResponse.getError().getCode());
                switch (code){
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mLoginPresenter.loginSuccess(String.valueOf(loginResponse.getError().getDescription()));
                        mLoginPresenter.saveUser(String.valueOf(loginResponse.getToken()), String.valueOf(loginResponse.getAvatar()),
                                String.valueOf(loginResponse.getFullname()), Utils.getDeviceId());
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mLoginPresenter.showToast(String.valueOf(loginResponse.getError().getDescription()));
                        break;
                    default:
                        mLoginPresenter.loginFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mLoginPresenter.loginFalse();
            }
        });
    }
}
