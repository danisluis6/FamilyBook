package com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment;

import android.support.v7.widget.RecyclerView;

public interface BranchModel {
    void getGenealogiesByUsername(String token);
   // void getBranchesByGenealogyId(String token, int genealogyId);
    void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder);
}
