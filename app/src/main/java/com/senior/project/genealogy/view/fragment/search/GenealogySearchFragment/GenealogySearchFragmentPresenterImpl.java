package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.Search;

import java.util.List;

public class GenealogySearchFragmentPresenterImpl implements GenealogySearchFragmentPresenter {

    private GenealogySearchModel mGenealogySearchModel;
    private GenealogySearchFragmentView mGenealogySearchFragmentView;

    public GenealogySearchFragmentPresenterImpl(GenealogySearchFragmentView genealogySearchFragmentView) {
        mGenealogySearchFragmentView = genealogySearchFragmentView;
        mGenealogySearchModel = new GenealogySearchModelImpl(this);
    }

    @Override
    public void searchGenealogyByName(Search search, String token) {
        mGenealogySearchFragmentView.showProgressDialog();
        mGenealogySearchModel.searchGenealogyByName(search, token);
    }

    @Override
    public void searchGenealogyByNameSuccess(List<Genealogy> genealogyList) {
        mGenealogySearchFragmentView.closeProgressDialog();
        mGenealogySearchFragmentView.showGenealogy(genealogyList);
    }

    @Override
    public void searchGenealogyByNameFalse() {
        mGenealogySearchFragmentView.closeProgressDialog();
    }

}
