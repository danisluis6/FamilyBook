package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface DialogNodeFragmentView {
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void closeDialogFragment(List<People> peopleList);
}
