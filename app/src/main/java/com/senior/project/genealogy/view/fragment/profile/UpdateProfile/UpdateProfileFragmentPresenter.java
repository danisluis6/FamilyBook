package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import com.senior.project.genealogy.response.User;

public interface UpdateProfileFragmentPresenter {
    void updateProfile(String token, User user);
    void updateProfileSuccess(User user);
    void updateProfileFalse();
    void showToast(String s);
}
