package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

public interface UpdateGenealogyFragmentPresenter {
    void updateGenealogy(Genealogy genealogy, String token);
    void updateGenealogySuccess(Genealogy genealogy);
    void updateGenealogyFalse();
    void showToast(String s);
}
