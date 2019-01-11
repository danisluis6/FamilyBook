package com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public class BranchFragmentPresenterImpl implements BranchFragmentPresenter {

    private BranchModel mBranchModel;
    private BranchFragmentView mBranchFragmentView;

    public BranchFragmentPresenterImpl(BranchFragmentView branchFragmentView) {
        mBranchFragmentView = branchFragmentView;
        mBranchModel = new BranchModelImpl(this);
    }

    @Override
    public void getGenealogiesByUsername(String token) {
        mBranchFragmentView.showProgressDialog();
        mBranchModel.getGenealogiesByUsername(token);
    }

    @Override
    public void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList) {
        if(mBranchFragmentView != null){
            mBranchFragmentView.closeProgressDialog();
            mBranchFragmentView.addItemsOnSpinnerGenealogy(genealogyList);
        }
    }

    @Override
    public void getGenealogiesByUsernameFalse() {
        mBranchFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mBranchFragmentView.closeProgressDialog();
        mBranchFragmentView.showToast(s);
    }

//    @Override
//    public void getBranchesByGenealogyId(String token, int genealogyId) {
//        mBranchFragmentView.showProgressDialog();
//        mBranchModel.getBranchesByGenealogyId(token, genealogyId);
//    }
//
//    @Override
//    public void getBranchesByGenealogyIdSuccess(List<Branch> branchList) {
//        mBranchFragmentView.closeProgressDialog();
//        mBranchFragmentView.showBranch(branchList);
//    }
//
//    @Override
//    public void getBranchesByGenealogyIdFalse() {
//        mBranchFragmentView.closeProgressDialog();
//    }

    @Override
    public void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder) {
        mBranchFragmentView.showProgressDialog();
        mBranchModel.deleteBranch(branchId, token, viewHolder);
    }

    @Override
    public void deleteBranchSuccess(RecyclerView.ViewHolder viewHolder) {
        mBranchFragmentView.closeProgressDialog();
        mBranchFragmentView.deleteItemBranch(viewHolder);
    }

    @Override
    public void deleteBranchFalse() {
        mBranchFragmentView.closeProgressDialog();
    }
}
