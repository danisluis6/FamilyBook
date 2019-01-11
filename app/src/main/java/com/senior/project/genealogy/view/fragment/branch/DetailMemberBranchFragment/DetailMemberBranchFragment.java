package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerItemMemberTouchHelper;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerViewItemMemberAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMemberBranchFragment extends Fragment implements DetailMemberBranchFragmentView, RecyclerItemMemberTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.rcvMember)
    RecyclerView rcvMember;

    private String token;
    private Branch branch;
    private ProgressDialog mProgressDialog;
    private RecyclerViewItemMemberAdapter mRcvAdapter;
    private List<User> users;
    private DetailMemberBranchFragmentPresenterImpl detailMemberBranchFragmentPresenterImpl;

    public DetailMemberBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_member, container, false);
        ButterKnife.bind(this, view);
        branch = (Branch) getArguments().getSerializable("branch");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserBranchPermission userBranchPermission = new UserBranchPermission(true, branch.getId());
        detailMemberBranchFragmentPresenterImpl = new DetailMemberBranchFragmentPresenterImpl(this);
        detailMemberBranchFragmentPresenterImpl.getMemberOfBranch(token, userBranchPermission);
        return view;
    }

    @Override
    public void showMember(List<User> userList) {
        users = new ArrayList<>();
        if(userList == null){

        }
        else {
            users.addAll(userList);
        }

        mRcvAdapter = new RecyclerViewItemMemberAdapter(getActivity(), users, branch, detailMemberBranchFragmentPresenterImpl);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvMember.setLayoutManager(layoutManager);
        rcvMember.setAdapter(mRcvAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemMemberTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rcvMember);
    }

    @Override
    public void changeRoleMemberOfBranchSuccess(UserBranchPermission userBranchPermission) {
        mRcvAdapter.changeRoleMemberSuccess(userBranchPermission);
    }

    @Override
    public void changeRoleMemberOfBranchFalse() {
        mRcvAdapter.changeRoleMemberFalse();
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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewItemMemberAdapter.RecyclerViewHolder) {
            final int deletedIndex = viewHolder.getAdapterPosition();
            showAlertDialog("Delete", "Are you sure?", "Delete", "Cancel", viewHolder);
        }
    }

    public void showAlertDialog(String title, String message, String positive, String negative, final RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String memberName = users.get(viewHolder.getAdapterPosition()).getUsername();
//                branchFragmentPresenterImpl.deleteBranch(branchId, token, viewHolder);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mRcvAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void updateMember(User user) {
        mRcvAdapter.updateMember(user);
    }
}
