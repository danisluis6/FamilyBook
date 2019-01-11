package com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment;

import com.senior.project.genealogy.service.ApplicationApi;

public class DetailBranchModelImpl implements DetailBranchModel {
    private DetailBranchFragmentPresenter mDetailBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailBranchModelImpl(DetailBranchFragmentPresenter detailBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailBranchFragmentPresenter = detailBranchFragmentPresenter;
    }
}
