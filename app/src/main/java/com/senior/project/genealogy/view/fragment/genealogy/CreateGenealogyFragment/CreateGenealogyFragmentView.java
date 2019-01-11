package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;
import java.util.List;

public interface CreateGenealogyFragmentView {
    void closeFragment(List<Genealogy> genealogyList);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
}
