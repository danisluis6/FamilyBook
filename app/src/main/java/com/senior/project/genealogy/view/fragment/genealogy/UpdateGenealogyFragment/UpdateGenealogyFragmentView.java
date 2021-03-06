package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

public interface UpdateGenealogyFragmentView {
    void showGenealogy(Genealogy genealogy);
    void showToast(String message);
    void showProgressDialog();
    void closeProgressDialog();
    void closeFragment(Genealogy genealogy);
}
