package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.People;
import java.util.List;

public class PeopleSearchFragmentPresenterImpl implements PeopleSearchFragmentPresenter {

    private PeopleSearchModel mPeopleSearchModel;
    private PeopleSearchFragmentView mPeopleSearchFragmentView;

    public PeopleSearchFragmentPresenterImpl(PeopleSearchFragmentView peopleSearchFragmentView) {
        mPeopleSearchFragmentView = peopleSearchFragmentView;
        mPeopleSearchModel = new PeopleSearchModelImpl(this);
    }

    @Override
    public void searchBranchByPeople(People people, String token) {
        mPeopleSearchFragmentView.showProgressDialog();
        mPeopleSearchModel.searchBranchByPeople(people, token);
    }

    @Override
    public void searchBranchByPeopleSuccess(List<People> peopleList) {
        mPeopleSearchFragmentView.closeProgressDialog();
        mPeopleSearchFragmentView.showPeople(peopleList);
    }

    @Override
    public void searchBranchByPeopleFalse() {
        mPeopleSearchFragmentView.closeProgressDialog();
    }

    @Override
    public void getBranchByBranchId(Integer branchId, String token) {
        mPeopleSearchFragmentView.showProgressDialog();
        mPeopleSearchModel.getBranchByBranchId(branchId, token);
    }

    @Override
    public void getBranchByBranchIdSuccess(Branch branch) {
        mPeopleSearchFragmentView.closeProgressDialog();
        mPeopleSearchFragmentView.showBranch(branch);
    }

    @Override
    public void getBranchByBranchIdFalse() {
        mPeopleSearchFragmentView.closeProgressDialog();
    }
}
