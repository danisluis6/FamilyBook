package com.senior.project.genealogy.view.activity.register;

import com.senior.project.genealogy.response.User;

public interface RegisterPresenter {
    void register(User user);
    void registerSuccess(String msg);
    void registerFalse();
    void showToast(String s);
    void saveUser(String token, String avatar, String fullname);
}
