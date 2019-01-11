package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;

import com.senior.project.genealogy.service.ApplicationApi;

public class DetailGenealogyModelImpl implements DetailGenealogyModel {
    private DetailGenealogyFragmentPresenter mDetailGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailGenealogyModelImpl(DetailGenealogyFragmentPresenter detailGenealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailGenealogyFragmentPresenter = detailGenealogyFragmentPresenter;
    }
}
