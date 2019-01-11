package com.senior.project.genealogy.view.fragment.profile.ShowProfile;

import com.senior.project.genealogy.response.User;

public interface ProfileFragmentPresenter {
    void getProfile(String token);
    void getProfileSuccess(User user);
    void getProfileFalse();
    void showToast(String s);
}
