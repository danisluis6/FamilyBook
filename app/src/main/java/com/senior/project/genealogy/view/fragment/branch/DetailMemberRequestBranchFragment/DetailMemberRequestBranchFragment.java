package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerViewItemRequestMemberAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMemberRequestBranchFragment extends Fragment implements DetailMemberRequestBranchFragmentView{

    @BindView(R.id.rcvMemberRequest)
    RecyclerView rcvMemberRequest;

    private Branch branch;
    private String token;
    private List<User> users;
    private ProgressDialog mProgressDialog;
    private RecyclerViewItemRequestMemberAdapter mRcvAdapter;
    private DetailMemberRequestBranchFragmentPresenterImpl detailMemberRequestBranchFragmentPresenterImpl;

    public DetailMemberRequestBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_member_request, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        branch = (Branch) getArguments().getSerializable("branch");
        UserBranchPermission userBranchPermission = new UserBranchPermission(false, branch.getId());
        detailMemberRequestBranchFragmentPresenterImpl = new DetailMemberRequestBranchFragmentPresenterImpl(this);
        detailMemberRequestBranchFragmentPresenterImpl.getRequestMemberOfBranch(token, userBranchPermission);
        return view;
    }

    @Override
    public void showRequestMember(List<User> userList) {
        users = new ArrayList<>();

        if(userList == null){
//            showToast("You didn't have any member");
        }
        else {
            users.addAll(userList);
        }

        mRcvAdapter = new RecyclerViewItemRequestMemberAdapter(getActivity(), users, branch.getId(), detailMemberRequestBranchFragmentPresenterImpl);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvMemberRequest.setLayoutManager(layoutManager);
        rcvMemberRequest.setAdapter(mRcvAdapter);
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void acceptMember(int position, User member) {
        mRequestMemberInterface.sendDataToListMember(member);
        showToast("Your branch have 1 new member!");
        mRcvAdapter.removeItem(position);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void declineMember(int position) {
        mRcvAdapter.removeItem(position);
    }

    public interface RequestMemberInterface{
        void sendDataToListMember(User member);
    }

    public RequestMemberInterface mRequestMemberInterface;

    public void attackInterface(RequestMemberInterface requestMemberInterface){
        mRequestMemberInterface = requestMemberInterface;
    }

}
