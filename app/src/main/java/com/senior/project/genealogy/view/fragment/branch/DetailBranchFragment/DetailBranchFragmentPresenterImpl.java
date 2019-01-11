package com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment;

public class DetailBranchFragmentPresenterImpl implements DetailBranchFragmentPresenter {

    private DetailBranchModel mDetailBranchModel;
    private DetailBranchFragmentView mDetailBranchFragmentView;

    public DetailBranchFragmentPresenterImpl(DetailBranchFragmentView detailBranchFragmentView) {
        mDetailBranchFragmentView = detailBranchFragmentView;
        mDetailBranchModel = new DetailBranchModelImpl(this);
    }

}
