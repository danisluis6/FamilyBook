package com.senior.project.genealogy.view.fragment.branch.UpdateBranchFragment;

import com.senior.project.genealogy.response.Branch;

public interface UpdateBranchFragmentView {
    void showBranch(Branch branch);
    void showToast(String message);
    void showProgressDialog();
    void closeProgressDialog();
    void closeFragment(Branch branch);
}
