package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

public interface DetailMemberRequestBranchModel {
    void getRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token);
    void declineRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token, int position);
    void acceptRequestMemberOfBranch(UserBranchPermission userBranchPermission, User user, String token, int position);
}
