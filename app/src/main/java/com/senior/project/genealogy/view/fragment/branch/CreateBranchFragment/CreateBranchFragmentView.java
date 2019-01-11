package com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment;

import com.senior.project.genealogy.response.Branch;

import java.util.List;

public interface CreateBranchFragmentView {
    void closeFragment(List<Branch> branchList);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
}
