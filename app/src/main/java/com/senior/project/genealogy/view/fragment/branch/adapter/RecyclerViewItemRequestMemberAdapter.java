package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment.DetailMemberRequestBranchFragmentPresenterImpl;

import java.util.List;

public class RecyclerViewItemRequestMemberAdapter extends RecyclerView.Adapter<RecyclerViewItemRequestMemberAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<User> mUser;
    private int branchId;
    private DetailMemberRequestBranchFragmentPresenterImpl mDetailMemberRequestBranchFragmentPresenter;

    private String token;

    public RecyclerViewItemRequestMemberAdapter(Context mContext, List<User> mUser, int branchId, DetailMemberRequestBranchFragmentPresenterImpl mDetailMemberRequestBranchFragmentPresenter) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.branchId = branchId;
        this.mDetailMemberRequestBranchFragmentPresenter = mDetailMemberRequestBranchFragmentPresenter;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member_request, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        final User user = mUser.get(position);
        final String memberName = mUser.get(position).getFullname();
        final String memberUsername = mUser.get(position).getUsername();

        holder.txtName.setText(memberName);
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailMemberRequestBranchFragmentPresenter.acceptRequestMemberOfBranch(token, new UserBranchPermission(memberUsername, branchId), user, holder.getAdapterPosition());
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailMemberRequestBranchFragmentPresenter.declineRequestMemberOfBranch(token, new UserBranchPermission(memberUsername, branchId), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public void removeItem(int position) {
        mUser.remove(position);
        notifyItemRemoved(position);
    }

    public void updateRequestMember(User user) {
        mUser.add(user);
        notifyDataSetChanged();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        Button btnAccept, btnDecline;
        FrameLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnDecline = (Button) itemView.findViewById(R.id.btnDecline);
            line = (FrameLayout) itemView.findViewById(R.id.lineBranch);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String username);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
