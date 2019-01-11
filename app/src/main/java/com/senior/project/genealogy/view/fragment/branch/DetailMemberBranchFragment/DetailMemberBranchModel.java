package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.UserBranchPermission;

public interface DetailMemberBranchModel {
    void getMemberOfBranch(UserBranchPermission userBranchPermission, String token);
    void changeRoleMemberOfBranch(UserBranchPermission userBranchPermission, String token);
}
