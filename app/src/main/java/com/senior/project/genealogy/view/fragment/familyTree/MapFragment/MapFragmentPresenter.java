package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.UserBranchPermission;
import java.util.List;

public interface MapFragmentPresenter {
    void showToast(String s);
    void getFamilyTreeByBranchId(int branchId, String token);
    void getFamilyTreeByBranchIdSuccess(List<People> peopleList);
    void getFamilyTreeByBranchIdFalse();

    void deletePeople(int peopleId, String token);
    void deletePeopleSuccess(int peopleId);
    void deletePeopleFalse();

    void joinBranch(UserBranchPermission userBranchPermission, String token);
    void joinBranchSuccess();
    void joinBranchFalse();

    void outBranch(UserBranchPermission userBranchPermission, String token);
    void outBranchSuccess();
    void outBranchFalse();
}
