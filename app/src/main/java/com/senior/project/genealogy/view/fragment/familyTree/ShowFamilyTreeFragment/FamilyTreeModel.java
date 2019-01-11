package com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment;

import android.support.v7.widget.RecyclerView;

public interface FamilyTreeModel {
    void getGenealogiesByUsername(String token);
    void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder);
}
