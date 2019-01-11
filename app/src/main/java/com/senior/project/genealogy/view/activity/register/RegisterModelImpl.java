package com.senior.project.genealogy.view.activity.register;

import android.app.ProgressDialog;
import android.util.Log;

import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModelImpl implements RegisterModel {
    private RegisterPresenter mRegisterPresenter;
    private ApplicationApi mApplicationApi;

    public RegisterModelImpl(RegisterPresenter registerPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mRegisterPresenter = registerPresenter;
    }

    @Override
    public void register(final User user) {
        Call<LoginResponse> call = mApplicationApi.getClient().create(UserApi.class).register(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                int code = Integer.parseInt(loginResponse.getError().getCode());
                switch (code){
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mRegisterPresenter.registerSuccess(String.valueOf(loginResponse.getError().getDescription()));
                        mRegisterPresenter.saveUser(String.valueOf(loginResponse.getToken()), String.valueOf(loginResponse.getAvatar()),
                                String.valueOf(loginResponse.getFullname()));
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_EXISTED:
                        mRegisterPresenter.showToast(String.valueOf(loginResponse.getError().getDescription()));
                        break;
                    default:
                        mRegisterPresenter.registerFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                mRegisterPresenter.registerFalse();
            }
        });
    }
}