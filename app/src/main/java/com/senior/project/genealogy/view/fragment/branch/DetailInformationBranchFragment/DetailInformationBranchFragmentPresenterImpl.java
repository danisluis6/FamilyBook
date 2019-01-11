package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;

public class DetailInformationBranchFragmentPresenterImpl implements DetailInformationBranchFragmentPresenter {

    private DetailInformationBranchModel mDetailInformationBranchModel;
    private DetailInformationBranchFragmentView mDetailInformationBranchFragmentView;

    public DetailInformationBranchFragmentPresenterImpl(DetailInformationBranchFragmentView detailInformationBranchFragmentView) {
        mDetailInformationBranchFragmentView = detailInformationBranchFragmentView;
        mDetailInformationBranchModel = new DetailInformationBranchModelImpl(this);
    }

}
