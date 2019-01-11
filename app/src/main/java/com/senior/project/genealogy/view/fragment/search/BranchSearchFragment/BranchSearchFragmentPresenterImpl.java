package com.senior.project.genealogy.view.fragment.search.BranchSearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Search;

import java.util.List;

public class BranchSearchFragmentPresenterImpl implements BranchSearchFragmentPresenter {

    private BranchSearchModel mBranchSearchModel;
    private BranchSearchFragmentView mBranchSearchFragmentView;

    public BranchSearchFragmentPresenterImpl(BranchSearchFragmentView branchSearchFragmentView) {
        mBranchSearchFragmentView = branchSearchFragmentView;
        mBranchSearchModel = new BranchSearchModelImpl(this);
    }

    @Override
    public void searchBranchByName(Search search, String token) {
        mBranchSearchFragmentView.showProgressDialog();
        mBranchSearchModel.searchBranchByName(search, token);
    }

    @Override
    public void searchBranchByNameSuccess(List<Branch> branchList) {
        mBranchSearchFragmentView.closeProgressDialog();
        mBranchSearchFragmentView.showBranch(branchList);
    }

    @Override
    public void searchBranchByNameFalse() {
        mBranchSearchFragmentView.closeProgressDialog();
    }
}
