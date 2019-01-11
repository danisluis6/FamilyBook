package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.support.v7.widget.RecyclerView;

public interface GenealogyModel {
    void getGenealogiesByUsername(String token);
    void deleteGenealogy(int genealogyId, String token, RecyclerView.ViewHolder viewHolder);
}
