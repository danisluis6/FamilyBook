package com.senior.project.genealogy.view.activity.splash;

public interface SplashView {
    void showActivity(Class<?> cls);
    void saveUser(String token, String avatar, String fullname, String deviceId);
}
