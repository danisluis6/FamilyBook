package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public class GenealogyFragmentPresenterImpl implements GenealogyFragmentPresenter {

    private GenealogyModel mGenealogyModel;
    private GenealogyFragmentView mGenealogyFragmentView;

    public GenealogyFragmentPresenterImpl(GenealogyFragmentView genealogyFragmentView) {
        mGenealogyFragmentView = genealogyFragmentView;
        mGenealogyModel = new GenealogyModelImpl(this);
    }

    @Override
    public void getGenealogiesByUsername(String token, Boolean check) {
        if(check == true){
            mGenealogyFragmentView.showProgressDialog();
        }
        mGenealogyModel.getGenealogiesByUsername(token);
    }

    @Override
    public void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList) {
        if(mGenealogyFragmentView != null){
            mGenealogyFragmentView.closeProgressDialog();
        }
        mGenealogyFragmentView.showGenealogy(genealogyList);
    }

    @Override
    public void getGenealogiesByUsernameFalse() {
        mGenealogyFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mGenealogyFragmentView.closeProgressDialog();
        mGenealogyFragmentView.showToast(s);
    }

    @Override
    public void deleteGenealogy(int genealogyId, String token, RecyclerView.ViewHolder viewHolder) {
        mGenealogyFragmentView.showProgressDialog();
        mGenealogyModel.deleteGenealogy(genealogyId, token, viewHolder);
    }

    @Override
    public void deleteGenealogySuccess(RecyclerView.ViewHolder viewHolder) {
        mGenealogyFragmentView.closeProgressDialog();
        mGenealogyFragmentView.deleteItemGenealogy(viewHolder);
    }

    @Override
    public void deleteGenealogyFalse() {
        mGenealogyFragmentView.closeProgressDialog();
    }
}
