package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public interface DetailMemberBranchFragmentPresenter {
    void getMemberOfBranch(String token, UserBranchPermission userBranchPermission);
    void getMemberOfBranchSuccess(List<User> userList);
    void getMemberOfBranchFalse();

    void changeRoleMemberOfBranch(String token, UserBranchPermission userBranchPermission);
    void changeRoleMemberOfBranchSuccess(UserBranchPermission userBranchPermission);
    void changeRoleMemberOfBranchFalse();
}
