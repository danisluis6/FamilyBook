package com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public class FamilyTreeFragmentPresenterImpl implements FamilyTreeFragmentPresenter {

    private FamilyTreeModel mFamilyTreeModel;
    private FamilyTreeFragmentView mFamilyTreeFragmentView;

    public FamilyTreeFragmentPresenterImpl(FamilyTreeFragmentView familyTreeFragmentView) {
        mFamilyTreeFragmentView = familyTreeFragmentView;
        mFamilyTreeModel = new FamilyTreeModelImpl(this);
    }

    @Override
    public void getGenealogiesByUsername(String token) {
        mFamilyTreeFragmentView.showProgressDialog();
        mFamilyTreeModel.getGenealogiesByUsername(token);
    }

    @Override
    public void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList) {
        mFamilyTreeFragmentView.closeProgressDialog();
        mFamilyTreeFragmentView.addItemsOnSpinnerGenealogy(genealogyList);
    }

    @Override
    public void getGenealogiesByUsernameFalse() {
        mFamilyTreeFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mFamilyTreeFragmentView.closeProgressDialog();
        mFamilyTreeFragmentView.showToast(s);
    }

    @Override
    public void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder) {
        mFamilyTreeFragmentView.showProgressDialog();
        mFamilyTreeModel.deleteBranch(branchId, token, viewHolder);
    }

    @Override
    public void deleteBranchSuccess(RecyclerView.ViewHolder viewHolder) {
        mFamilyTreeFragmentView.closeProgressDialog();
        mFamilyTreeFragmentView.deleteItemBranch(viewHolder);
    }

    @Override
    public void deleteBranchFalse() {
        mFamilyTreeFragmentView.closeProgressDialog();
    }
}
