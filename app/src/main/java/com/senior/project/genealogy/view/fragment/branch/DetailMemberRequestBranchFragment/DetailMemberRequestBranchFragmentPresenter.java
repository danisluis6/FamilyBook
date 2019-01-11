package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public interface DetailMemberRequestBranchFragmentPresenter {
    void getRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission);
    void getRequestMemberOfBranchSuccess(List<User> userList);
    void getRequestMemberOfBranchFalse();

    void declineRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission, int position);
    void declineRequestMemberOfBranchSuccess(int position);
    void declineRequestMemberOfBranchFalse();

    void acceptRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission, User user, int position);
    void acceptRequestMemberOfBranchSuccess(int position, User user);
    void acceptRequestMemberOfBranchFalse();


}
