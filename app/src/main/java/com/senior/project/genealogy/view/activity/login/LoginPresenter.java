package com.senior.project.genealogy.view.activity.login;

import com.senior.project.genealogy.response.User;

public interface LoginPresenter {
    void login(User user, boolean isCheckLoginAgain);
    void loginSuccess(String msg);
    void loginFalse();
    void showToast(String s);
    void saveUser(String token, String avatar, String fullname, String deviceId);
}
