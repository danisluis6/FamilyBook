package com.senior.project.genealogy.view.fragment.search.BranchSearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Search;

import java.util.List;

public interface BranchSearchFragmentPresenter {
    void searchBranchByName(Search search, String token);
    void searchBranchByNameSuccess(List<Branch> branchList);
    void searchBranchByNameFalse();
}
