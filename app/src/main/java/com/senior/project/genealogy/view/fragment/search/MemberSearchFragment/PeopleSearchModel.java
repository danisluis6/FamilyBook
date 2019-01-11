package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import com.senior.project.genealogy.response.People;

public interface PeopleSearchModel {
    void searchBranchByPeople(People people, String token);
    void getBranchByBranchId(Integer branchId, String token);
}
