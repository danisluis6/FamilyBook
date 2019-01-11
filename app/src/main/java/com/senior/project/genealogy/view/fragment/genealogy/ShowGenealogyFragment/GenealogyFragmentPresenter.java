package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface GenealogyFragmentPresenter {
    void getGenealogiesByUsername(String toke, Boolean check);
    void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList);
    void getGenealogiesByUsernameFalse();
    void deleteGenealogy(int genealogyId, String token, RecyclerView.ViewHolder viewHolder);
    void deleteGenealogySuccess(RecyclerView.ViewHolder viewHolder);
    void deleteGenealogyFalse();
    void showToast(String s);
}
