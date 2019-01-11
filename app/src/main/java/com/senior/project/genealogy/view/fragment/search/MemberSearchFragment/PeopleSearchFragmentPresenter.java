package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.People;

import java.util.List;

public interface PeopleSearchFragmentPresenter {
    void searchBranchByPeople(People people, String token);
    void searchBranchByPeopleSuccess(List<People> peopleList);
    void searchBranchByPeopleFalse();

    void getBranchByBranchId(Integer branchId, String token);
    void getBranchByBranchIdSuccess(Branch branch);
    void getBranchByBranchIdFalse();
}
