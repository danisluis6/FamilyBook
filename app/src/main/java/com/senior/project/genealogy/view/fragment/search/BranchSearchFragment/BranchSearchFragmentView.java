package com.senior.project.genealogy.view.fragment.search.BranchSearchFragment;

import com.senior.project.genealogy.response.Branch;

import java.util.List;

public interface BranchSearchFragmentView {
    void showToast(String msg);
    void showBranch(List<Branch> branchList);
    void showProgressDialog();
    void closeProgressDialog();
}
