package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import com.senior.project.genealogy.response.People;
import java.util.List;

public class DialogProfileFragmentPresenterImpl implements DialogProfileFragmentPresenter {

    private ProfileModel mProfileModel;
    private DialogProfileFragmentView mDialogProfileFragmentView;

    public DialogProfileFragmentPresenterImpl(DialogProfileFragmentView dialogProfileFragmentView) {
        mDialogProfileFragmentView = dialogProfileFragmentView;
        mProfileModel = new ProfileModelImpl(this);
    }

    @Override
    public void getRelative(int peopleId, String token) {
        mDialogProfileFragmentView.showProgressDialog();
        mProfileModel.getRelative(peopleId, token);
    }

    @Override
    public void getRelativeSuccess(List<People> peopleList) {
        mDialogProfileFragmentView.closeProgressDialog();
        mDialogProfileFragmentView.showRelationMap(peopleList);
    }

    @Override
    public void getRelativeFalse() {
        mDialogProfileFragmentView.closeProgressDialog();
    }

}
