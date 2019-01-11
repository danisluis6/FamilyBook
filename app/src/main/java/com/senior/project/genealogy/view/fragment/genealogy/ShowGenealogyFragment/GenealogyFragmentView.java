package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface GenealogyFragmentView {
    void showGenealogy(List<Genealogy> genealogyList);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void deleteItemGenealogy(RecyclerView.ViewHolder viewHolder);
}
