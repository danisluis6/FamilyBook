package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.People;

import java.util.List;

public interface PeopleSearchFragmentView {
    void showToast(String msg);
    void showPeople(List<People> peopleList);
    void showProgressDialog();
    void closeProgressDialog();
    void getBranchById(Integer branchId);
    void showBranch(Branch branch);
}
