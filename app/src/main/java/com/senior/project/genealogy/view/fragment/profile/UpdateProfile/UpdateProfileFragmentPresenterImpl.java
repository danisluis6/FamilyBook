package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentPresenter;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentView;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileModel;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileModelImpl;

public class UpdateProfileFragmentPresenterImpl implements UpdateProfileFragmentPresenter {

    private UpdateProfileModel mUpdateProfileModel;
    private UpdateProfileFragmentView mUpdateProfileFragmentView;

    public UpdateProfileFragmentPresenterImpl(UpdateProfileFragmentView updateProfileFragmentView) {
        mUpdateProfileFragmentView = updateProfileFragmentView;
        mUpdateProfileModel = new UpdateProfileModelImpl(this);
    }

    @Override
    public void updateProfile(String token, User user) {
        mUpdateProfileFragmentView.showProgressDialog();
        mUpdateProfileModel.updateProfile(token, user);
    }

    @Override
    public void updateProfileSuccess(User user) {
        mUpdateProfileFragmentView.closeProgressDialog();
        mUpdateProfileFragmentView.closeFragment(user);
    }

    @Override
    public void updateProfileFalse() {
        mUpdateProfileFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mUpdateProfileFragmentView.closeProgressDialog();
        mUpdateProfileFragmentView.showToast(s);
    }

}
