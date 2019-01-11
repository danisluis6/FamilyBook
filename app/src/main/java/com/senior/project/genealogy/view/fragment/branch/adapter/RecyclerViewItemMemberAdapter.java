package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragmentPresenter;

import java.util.List;

public class RecyclerViewItemMemberAdapter extends RecyclerView.Adapter<RecyclerViewItemMemberAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<User> mUser;
    private Branch branch;
    private DetailMemberBranchFragmentPresenter mDetailMemberBranchFragmentPresenter;

    private String token;

    public RecyclerViewItemMemberAdapter(Context mContext, List<User> mUser, Branch branch, DetailMemberBranchFragmentPresenter mDetailMemberBranchFragmentPresenter) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.branch = branch;
        this.mDetailMemberBranchFragmentPresenter = mDetailMemberBranchFragmentPresenter;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        final String memberName = mUser.get(position).getFullname();
        final String memberUsername = mUser.get(position).getUsername();
        final int memberRole = mUser.get(position).getRole();
        holder.txtName.setText(memberName);

        if (branch.getRole() == Constants.ROLE.OWNER_ROLE){
            showSpinner(holder.spRole, mUser.get(position));
            switch (memberRole){
                case Constants.ROLE.OWNER_ROLE:
                    holder.spRole.setVisibility(View.GONE);
                    holder.txtRole.setText("Owner");
                    holder.txtRole.setVisibility(View.VISIBLE);
                    break;
                case Constants.ROLE.EDITOR_ROLE:
                    holder.spRole.setSelection(Constants.ROLE.EDITOR_ROLE - 2);
                    break;
                case Constants.ROLE.MEMBER_ROLE:
                    holder.spRole.setSelection(Constants.ROLE.MEMBER_ROLE - 2);
                    break;
            }
        } else {
            holder.spRole.setVisibility(View.GONE);
            holder.txtRole.setVisibility(View.VISIBLE);
            holder.txtRole.setPadding(0, 0, 20,0);
            switch (memberRole){
                case Constants.ROLE.OWNER_ROLE:
                    holder.txtRole.setText("Owner");
                    break;
                case Constants.ROLE.EDITOR_ROLE:
                    holder.txtRole.setText("Editor");
                    break;
                case Constants.ROLE.MEMBER_ROLE:
                    holder.txtRole.setText("Member");
                    break;
            }
        }

    }


    public void showSpinner(final Spinner spinner, final User user) {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(mContext, R.array.role_member_array, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                int roleId = 0;
                String role = spinner.getSelectedItem().toString();
                switch (role){
                    case "Editor":
                        roleId = Constants.ROLE.EDITOR_ROLE;
                        break;
                    case "Member":
                        roleId = Constants.ROLE.MEMBER_ROLE;
                        break;
                }

                if (user.getRole() != roleId){
                    UserBranchPermission userBranchPermission = new UserBranchPermission(user.getUsername(), branch.getId(), roleId);
                    mDetailMemberBranchFragmentPresenter.changeRoleMemberOfBranch(token, userBranchPermission);
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spinner.setAdapter(dataAdapter);
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public void removeItem(int position) {
        mUser.remove(position);
        notifyItemRemoved(position);
    }

    public void updateMember(User user) {
        mUser.add(user);
        notifyDataSetChanged();
    }

    public void changeRoleMemberSuccess(UserBranchPermission userBranchPermission) {
        for(User user : mUser){
            if (user.getUsername() == userBranchPermission.getUsername()){
                user.setRole(userBranchPermission.getBranch_permission_id());
            }
        }
        notifyDataSetChanged();
    }

    public void changeRoleMemberFalse() {
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        Spinner spRole;
        TextView txtRole;
        FrameLayout line;
        RelativeLayout viewBackground, viewForeground;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            spRole = (Spinner) itemView.findViewById(R.id.spRole);
            txtRole = (TextView) itemView.findViewById(R.id.txtRole);
            line = (FrameLayout) itemView.findViewById(R.id.lineBranch);
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

}
