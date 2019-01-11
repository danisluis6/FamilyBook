package com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile;

import com.senior.project.genealogy.response.People;

public interface UpdateDialogNodeFragmentView {
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void closeDialogFragment(People people);
}
