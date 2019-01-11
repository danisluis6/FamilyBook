package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface DialogProfileFragmentView {
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void showRelationMap(List<People> peopleList);
}
