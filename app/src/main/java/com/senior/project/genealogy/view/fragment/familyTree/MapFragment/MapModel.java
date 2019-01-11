package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.UserBranchPermission;

public interface MapModel {
    void getFamilyTreeByBranchId(int branchId, String token);
    void deletePeople(int peopleId, String token);
    void joinBranch(UserBranchPermission userBranchPermission, String token);
    void outBranch(UserBranchPermission userBranchPermission, String token);
}
