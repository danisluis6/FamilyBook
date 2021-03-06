package com.senior.project.genealogy.view.fragment.search.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment.DetailBranchFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewItemBranchAdapter extends RecyclerView.Adapter<RecyclerViewItemBranchAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<Branch> mBranches;

    public RecyclerViewItemBranchAdapter(Context mContext, FragmentManager mFragmentManager, List<Branch> mBranches) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.mBranches = mBranches;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_branch, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final int branchId = mBranches.get(position).getId();
        final String branchName = mBranches.get(position).getName();
        final String branchDescription = mBranches.get(position).getDescription();
        final int member = mBranches.get(position).getMember();
        final Date branchDate = mBranches.get(position).getDate();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final String date = formatter.format(branchDate);

        holder.txtBranchName.setText(branchName);
        holder.txtNumberOfPeople.setText(String.valueOf(member));
        holder.txtBranchDate.setText(date);

        final int role = mBranches.get(position).getRole();
        switch (role){
            case Constants.ROLE.OWNER_ROLE:
                holder.imgRole.setVisibility(View.VISIBLE);
                holder.imgRole.setImageResource(R.drawable.ic_admin);
                break;
            case Constants.ROLE.EDITOR_ROLE:
                holder.imgRole.setVisibility(View.VISIBLE);
                holder.imgRole.setImageResource(R.drawable.ic_mod);
                break;
            case Constants.ROLE.MEMBER_ROLE:
                holder.imgRole.setVisibility(View.VISIBLE);
                holder.imgRole.setImageResource(R.drawable.ic_member);
                break;
            case Constants.ROLE.WAITING:
                holder.imgRole.setVisibility(View.GONE);
                holder.txtWaiting.setVisibility(View.VISIBLE);
                break;
        }

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailBranchFragment mFragment = new DetailBranchFragment();
                Bundle bundle = new Bundle();
                Branch branch = new Branch(branchId, branchName, branchDescription, branchDate, member, role);
                bundle.putSerializable("branch", branch);
                mFragment.setArguments(bundle);
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.search_frame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBranches.size();
    }

    public void updateRcvBranch(List<Branch> branchList){
        mBranches.clear();
        mBranches.addAll(branchList);
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtBranchName;
        TextView txtNumberOfPeople;
        TextView txtBranchDate;
        TextView txtWaiting;
        ImageView imgRole;
        FrameLayout line;
        RelativeLayout viewBackground, viewForeground;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtBranchName = (TextView) itemView.findViewById(R.id.txtBranchName);
            txtNumberOfPeople = (TextView) itemView.findViewById(R.id.txtNumberOfPeople);
            txtBranchDate = (TextView) itemView.findViewById(R.id.txtBranchDate);
            imgRole = (ImageView) itemView.findViewById(R.id.imgRole);
            line = (FrameLayout) itemView.findViewById(R.id.lineBranch);
            txtWaiting = (TextView) itemView.findViewById(R.id.txtWaiting);
            viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewBackgroundBranch);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.viewForegroundBranch);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String username);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            if (type == HomeActivity.PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commitAllowingStateLoss();
            } else if (type == HomeActivity.PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commit();
            }
            mFragmentManager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
