package com.senior.project.genealogy.view.fragment.search.ShowBranchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface BranchFragmentView {
    void showBranch(List<Branch> branchList);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void addItemsOnSpinnerGenealogy(List<Genealogy> genealogyList);
}
