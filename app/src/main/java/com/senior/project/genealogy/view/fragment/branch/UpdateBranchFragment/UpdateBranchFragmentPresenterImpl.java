package com.senior.project.genealogy.view.fragment.branch.UpdateBranchFragment;

import com.senior.project.genealogy.response.Branch;

public class UpdateBranchFragmentPresenterImpl implements UpdateBranchFragmentPresenter {

    private UpdateBranchModel mUpdateBranchModel;
    private UpdateBranchFragmentView mUpdateBranchFragmentView;

    public UpdateBranchFragmentPresenterImpl(UpdateBranchFragmentView updateBranchFragmentView) {
        mUpdateBranchFragmentView = updateBranchFragmentView;
        mUpdateBranchModel = new UpdateBranchModelImpl(this);
    }

    @Override
    public void updateBranch(Branch branch, String token) {
        mUpdateBranchFragmentView.showProgressDialog();
        mUpdateBranchModel.updateBranch(branch, token);
    }

    @Override
    public void updateBranchSuccess(Branch branch) {
        mUpdateBranchFragmentView.closeProgressDialog();
        mUpdateBranchFragmentView.closeFragment(branch);
    }

    @Override
    public void updateBranchFalse() {
        mUpdateBranchFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mUpdateBranchFragmentView.closeProgressDialog();
        mUpdateBranchFragmentView.showToast(s);
    }
}
