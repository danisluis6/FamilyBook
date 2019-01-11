package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface MapFragmentView {
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void showMap(List<People> peopleList);
    void deletePeople(int peopleId);
    void joinBranchSuccess();
    void joinBranchFalse();
    void outBranchSuccess();
    void outBranchFalse();
}
