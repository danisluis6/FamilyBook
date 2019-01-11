package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public class DetailMemberBranchFragmentPresenterImpl implements DetailMemberBranchFragmentPresenter {

    private DetailMemberBranchModel mDetailMemberBranchModel;
    private DetailMemberBranchFragmentView mDetailMemberBranchFragmentView;

    public DetailMemberBranchFragmentPresenterImpl(DetailMemberBranchFragmentView detailMemberBranchFragmentView) {
        mDetailMemberBranchFragmentView = detailMemberBranchFragmentView;
        mDetailMemberBranchModel = new DetailMemberBranchModelImpl(this);
    }

    @Override
    public void getMemberOfBranch(String token, UserBranchPermission userBranchPermission) {
        mDetailMemberBranchFragmentView.showProgressDialog();
        mDetailMemberBranchModel.getMemberOfBranch(userBranchPermission, token);
    }

    @Override
    public void getMemberOfBranchSuccess(List<User> userList) {
        mDetailMemberBranchFragmentView.closeProgressDialog();
        mDetailMemberBranchFragmentView.showMember(userList);
    }

    @Override
    public void getMemberOfBranchFalse() {
        mDetailMemberBranchFragmentView.closeProgressDialog();
    }

    @Override
    public void changeRoleMemberOfBranch(String token, UserBranchPermission userBranchPermission) {
        mDetailMemberBranchFragmentView.showProgressDialog();
        mDetailMemberBranchModel.changeRoleMemberOfBranch(userBranchPermission, token);
    }

    @Override
    public void changeRoleMemberOfBranchSuccess(UserBranchPermission userBranchPermission) {
        mDetailMemberBranchFragmentView.closeProgressDialog();
        mDetailMemberBranchFragmentView.showToast("Success");
        mDetailMemberBranchFragmentView.changeRoleMemberOfBranchSuccess(userBranchPermission);
    }

    @Override
    public void changeRoleMemberOfBranchFalse() {
        mDetailMemberBranchFragmentView.closeProgressDialog();
        mDetailMemberBranchFragmentView.showToast("False");
        mDetailMemberBranchFragmentView.changeRoleMemberOfBranchFalse();
    }
}
