package com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface FamilyTreeFragmentPresenter {
    void getGenealogiesByUsername(String token);
    void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList);
    void getGenealogiesByUsernameFalse();
    void showToast(String s);
    void deleteBranch(int branchId, String token, RecyclerView.ViewHolder viewHolder);
    void deleteBranchSuccess(RecyclerView.ViewHolder viewHolder);
    void deleteBranchFalse();
}
