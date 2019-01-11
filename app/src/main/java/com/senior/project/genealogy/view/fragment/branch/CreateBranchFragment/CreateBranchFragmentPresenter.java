package com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment;

import com.senior.project.genealogy.response.Branch;

import java.util.List;

public interface CreateBranchFragmentPresenter {
    void createBranch(Branch branch, String token);
    void createBranchSuccess(List<Branch> branchList);
    void createBranchFalse();
    void showToast(String s);
}
