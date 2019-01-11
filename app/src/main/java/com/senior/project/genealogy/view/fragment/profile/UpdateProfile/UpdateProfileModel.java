package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import com.senior.project.genealogy.response.User;

public interface UpdateProfileModel {
    void updateProfile(String token, User user);
}
