package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public class DetailMemberRequestBranchFragmentPresenterImpl implements DetailMemberRequestBranchFragmentPresenter {

    private DetailMemberRequestBranchModel mDetailMemberRequestBranchModel;
    private DetailMemberRequestBranchFragmentView mDetailMemberRequestBranchFragmentView;

    public DetailMemberRequestBranchFragmentPresenterImpl(DetailMemberRequestBranchFragmentView detailMemberRequestBranchFragmentView) {
        mDetailMemberRequestBranchFragmentView = detailMemberRequestBranchFragmentView;
        mDetailMemberRequestBranchModel = new DetailMemberRequestBranchModelImpl(this);
    }

    @Override
    public void getRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission) {
        mDetailMemberRequestBranchFragmentView.showProgressDialog();
        mDetailMemberRequestBranchModel.getRequestMemberOfBranch(userBranchPermission, token);
    }

    @Override
    public void getRequestMemberOfBranchSuccess(List<User> userList) {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
        mDetailMemberRequestBranchFragmentView.showRequestMember(userList);
    }

    @Override
    public void getRequestMemberOfBranchFalse() {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
    }

    @Override
    public void declineRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission,int position) {
        mDetailMemberRequestBranchFragmentView.showProgressDialog();
        mDetailMemberRequestBranchModel.declineRequestMemberOfBranch(userBranchPermission, token, position);
    }

    @Override
    public void declineRequestMemberOfBranchSuccess(int position) {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
        mDetailMemberRequestBranchFragmentView.declineMember(position);
    }

    @Override
    public void declineRequestMemberOfBranchFalse() {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
    }

    @Override
    public void acceptRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission, User user, int position) {
        mDetailMemberRequestBranchFragmentView.showProgressDialog();
        mDetailMemberRequestBranchModel.acceptRequestMemberOfBranch(userBranchPermission, user, token, position);
    }

    @Override
    public void acceptRequestMemberOfBranchSuccess(int position, User user) {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
        mDetailMemberRequestBranchFragmentView.acceptMember(position, user);
    }

    @Override
    public void acceptRequestMemberOfBranchFalse() {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
    }
}
