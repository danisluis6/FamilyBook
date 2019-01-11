package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public class CreateGenealogyFragmentPresenterImpl implements CreateGenealogyFragmentPresenter {

    private GenealogyModel mGenealogyModel;
    private CreateGenealogyFragmentView mCreateGenealogFragmentView;

    public CreateGenealogyFragmentPresenterImpl(CreateGenealogyFragmentView createGenealogFragmentView) {
        mCreateGenealogFragmentView = createGenealogFragmentView;
        mGenealogyModel = new GenealogyModelImpl(this);
    }

    @Override
    public void createGenealogy(Genealogy genealogy,String token) {
        mCreateGenealogFragmentView.showProgressDialog();
        mGenealogyModel.createGenealogy(genealogy, token);
    }

    @Override
    public void createGenealogySuccess(List<Genealogy> genealogyList) {
        mCreateGenealogFragmentView.closeProgressDialog();
        mCreateGenealogFragmentView.closeFragment(genealogyList);
    }

    @Override
    public void createGenealogyFalse() {
        mCreateGenealogFragmentView.closeProgressDialog();
        mCreateGenealogFragmentView.showToast("FAlSE");
    }

    @Override
    public void showToast(String s) {
        mCreateGenealogFragmentView.closeProgressDialog();
        mCreateGenealogFragmentView.showToast(s);
    }

}
