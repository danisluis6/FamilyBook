package com.senior.project.genealogy.view.fragment.branch.UpdateBranchFragment;

import com.senior.project.genealogy.response.Branch;

public interface UpdateBranchFragmentPresenter {
    void updateBranch(Branch branch, String token);
    void updateBranchSuccess(Branch branch);
    void updateBranchFalse();
    void showToast(String s);
}
