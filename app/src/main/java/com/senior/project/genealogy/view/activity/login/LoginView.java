package com.senior.project.genealogy.view.activity.login;


public interface  LoginView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    void showProgressDialog();
    void closeProgressDialog();
    void saveUser(String token, String avatar, String fullname, String deviceId);
    void showLoginAgainDialog ();
    void saveAccount(String username, String password);
}
