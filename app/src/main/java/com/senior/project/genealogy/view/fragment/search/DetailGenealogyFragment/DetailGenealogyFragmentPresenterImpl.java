package com.senior.project.genealogy.view.fragment.search.DetailGenealogyFragment;

public class DetailGenealogyFragmentPresenterImpl implements DetailGenealogyFragmentPresenter {

    private DetailGenealogyModel mDetailGenealogyModel;
    private DetailGenealogyFragmentView mDetailGenealogyFragmentView;

    public DetailGenealogyFragmentPresenterImpl(DetailGenealogyFragmentView detailGenealogyFragmentView) {
        mDetailGenealogyFragmentView = detailGenealogyFragmentView;
        mDetailGenealogyModel = new DetailGenealogyModelImpl(this);
    }

}
