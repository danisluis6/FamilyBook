package com.senior.project.genealogy.view.fragment.profile.ShowProfile;

import com.senior.project.genealogy.response.User;

public class ProfileFragmentPresenterImpl implements ProfileFragmentPresenter {

    private ProfileModel mProfileModel;
    private ProfileFragmentView mProfileFragmentView;

    public ProfileFragmentPresenterImpl(ProfileFragmentView profileFragmentView) {
        mProfileFragmentView = profileFragmentView;
        mProfileModel = new ProfileModelImpl(this);
    }

    @Override
    public void getProfile(String token) {
        mProfileFragmentView.showProgressDialog();
        mProfileModel.getProfile(token);
    }

    @Override
    public void getProfileSuccess(User user) {
        mProfileFragmentView.closeProgressDialog();
        mProfileFragmentView.showProfile(user);
    }

    @Override
    public void getProfileFalse() {
        mProfileFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mProfileFragmentView.closeProgressDialog();
        mProfileFragmentView.showToast(s);
    }

}
