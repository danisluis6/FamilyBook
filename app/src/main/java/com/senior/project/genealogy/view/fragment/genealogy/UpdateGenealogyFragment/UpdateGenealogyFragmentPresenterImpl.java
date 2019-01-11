package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

public class UpdateGenealogyFragmentPresenterImpl implements UpdateGenealogyFragmentPresenter {

    private UpdateGenealogyModel mUpdateGenealogyModel;
    private UpdateGenealogyFragmentView mUpdateGenealogyFragmentView;

    public UpdateGenealogyFragmentPresenterImpl(UpdateGenealogyFragmentView updateGenealogyFragmentView) {
        mUpdateGenealogyFragmentView = updateGenealogyFragmentView;
        mUpdateGenealogyModel = new UpdateGenealogyModelImpl(this);
    }

    @Override
    public void updateGenealogy(Genealogy genealogy, String token) {
        mUpdateGenealogyFragmentView.showProgressDialog();
        mUpdateGenealogyModel.updateGenealogy(genealogy, token);
    }

    @Override
    public void updateGenealogySuccess(Genealogy genealogy) {
        mUpdateGenealogyFragmentView.closeProgressDialog();
        mUpdateGenealogyFragmentView.closeFragment(genealogy);
    }

    @Override
    public void updateGenealogyFalse() {
        mUpdateGenealogyFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mUpdateGenealogyFragmentView.closeProgressDialog();
        mUpdateGenealogyFragmentView.showToast(s);
    }
}
