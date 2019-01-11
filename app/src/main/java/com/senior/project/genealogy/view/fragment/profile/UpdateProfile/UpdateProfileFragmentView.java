package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import com.senior.project.genealogy.response.User;

public interface UpdateProfileFragmentView {
    void showProfile(User user);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void closeFragment(User user);
}
