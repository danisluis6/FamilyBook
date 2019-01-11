package com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile;

import com.senior.project.genealogy.response.People;

import java.util.List;

public class UpdateDialogNodeFragmentPresenterImpl implements UpdateDialogNodeFragmentPresenter {

    private UpdateNodeModel mUpdateNodeModel;
    private UpdateDialogNodeFragmentView mUpdateDialogNodeFragmentView;

    public UpdateDialogNodeFragmentPresenterImpl(UpdateDialogNodeFragmentView updateDialogNodeFragmentView) {
        mUpdateDialogNodeFragmentView = updateDialogNodeFragmentView;
        mUpdateNodeModel = new UpdateUpdateNodeModelImpl(this);
    }

    @Override
    public void updatePeople(People people, String token) {
        mUpdateDialogNodeFragmentView.showProgressDialog();
        mUpdateNodeModel.updatePeople(people, token);
    }

    @Override
    public void updatePeopleSuccess(People people) {
        mUpdateDialogNodeFragmentView.closeProgressDialog();
        mUpdateDialogNodeFragmentView.closeDialogFragment(people);
    }

    @Override
    public void updatePeopleFalse() {
        mUpdateDialogNodeFragmentView.closeProgressDialog();
    }

}
