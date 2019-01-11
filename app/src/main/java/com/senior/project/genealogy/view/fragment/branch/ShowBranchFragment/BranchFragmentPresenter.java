package com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface BranchFragmentPresenter {
    void getGenealogiesByUsername(String token);
    void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList);
    void getGenealogiesByUsernameFalse();
    void showToast(String s);
//    void getBranchesByGenealogyId(String token, int genealogyId);
//    void getBranchesByGenealogyIdSuccess(List<Branch> branchList);
//    void getBranchesByGenealogyIdFalse();
    void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder);
    void deleteBranchSuccess(RecyclerView.ViewHolder viewHolder);
    void deleteBranchFalse();
}
