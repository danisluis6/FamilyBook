package com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment;

import com.senior.project.genealogy.response.Branch;
import java.util.List;

public class CreateBranchFragmentPresenterImpl implements CreateBranchFragmentPresenter {

    private CreateBranchModel mCreateBranchModel;
    private CreateBranchFragmentView mCreateBranchFragmentView;

    public CreateBranchFragmentPresenterImpl(CreateBranchFragmentView createBranchFragmentView) {
        mCreateBranchFragmentView = createBranchFragmentView;
        mCreateBranchModel = new CreateBranchModelImpl(this);
    }

    @Override
    public void createBranch(Branch branch, String token) {
        mCreateBranchFragmentView.showProgressDialog();
        mCreateBranchModel.createBranch(branch, token);
    }

    @Override
    public void createBranchSuccess(List<Branch> branchList) {
        mCreateBranchFragmentView.closeProgressDialog();
        mCreateBranchFragmentView.closeFragment(branchList);
    }

    @Override
    public void createBranchFalse() {
        mCreateBranchFragmentView.closeProgressDialog();
        mCreateBranchFragmentView.showToast("FAlSE");
    }

    @Override
    public void showToast(String s) {
        mCreateBranchFragmentView.closeProgressDialog();
        mCreateBranchFragmentView.showToast(s);
    }

}
