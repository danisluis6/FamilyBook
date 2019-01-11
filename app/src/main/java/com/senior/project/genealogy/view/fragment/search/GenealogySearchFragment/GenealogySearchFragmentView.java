package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface GenealogySearchFragmentView {
    void showToast(String msg);
    void showGenealogy(List<Genealogy> genealogyList);
    void showProgressDialog();
    void closeProgressDialog();
}
