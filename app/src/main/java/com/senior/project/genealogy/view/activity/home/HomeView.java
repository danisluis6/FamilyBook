package com.senior.project.genealogy.view.activity.home;

public interface HomeView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    void saveAccount(String username, String password);
}
