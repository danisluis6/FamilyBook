package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface CreateGenealogyFragmentPresenter {
    void createGenealogy(Genealogy genealogy, String token);
    void createGenealogySuccess(List<Genealogy> genealogyList);
    void createGenealogyFalse();
    void showToast(String s);
}
