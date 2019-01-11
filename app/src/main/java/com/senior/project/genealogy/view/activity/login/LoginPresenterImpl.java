package com.senior.project.genealogy.view.activity.login;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.activity.splash.SplashView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginModel mLoginModel;
    private LoginView mLoginView;
    private SplashView mSplashView;
    private boolean isCheckLoginAgain = false;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModelImpl(this);
    }

    public LoginPresenterImpl(SplashView splashView) {
        mSplashView = splashView;
        mLoginModel = new LoginModelImpl(this);
    }

    @Override
    public void login(final User user, boolean isCheckLoginAgain) {
        this.isCheckLoginAgain = isCheckLoginAgain;
        if (!isCheckLoginAgain) {
            mLoginView.showProgressDialog();
        }
        mLoginModel.login(user);
    }

    @Override
    public void loginFalse() {
        if (isCheckLoginAgain) {
            mSplashView.showActivity(LoginActivity.class);
        } else {
            mLoginView.saveAccount("", "");
            mLoginView.closeProgressDialog();
            mLoginView.showLoginAgainDialog();
        }
    }

    @Override
    public void loginSuccess(String msg) {
        if (isCheckLoginAgain) {
            mSplashView.showActivity(HomeActivity.class);
        } else {
            mLoginView.closeProgressDialog();
            showToast(msg);
            mLoginView.showActivity(HomeActivity.class);
        }
    }

    @Override
    public void showToast(String s) {
        mLoginView.closeProgressDialog();
        mLoginView.showToast(s);
    }

    @Override
    public void saveUser(String token, String avatar, String fullname, String deviceId) {
        if (isCheckLoginAgain) {
            mSplashView.saveUser(token, avatar, fullname, Utils.getDeviceId());
        } else {
            mLoginView.saveUser(token, avatar, fullname, Utils.getDeviceId());
        }
    }
}
