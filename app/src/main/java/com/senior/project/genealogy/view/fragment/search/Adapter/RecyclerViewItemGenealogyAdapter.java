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
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.search.DetailGenealogyFragment.DetailGenealogyFragment;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewItemGenealogyAdapter extends RecyclerView.Adapter<RecyclerViewItemGenealogyAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<Genealogy> genealogies;

    public RecyclerViewItemGenealogyAdapter(Context mContext, FragmentManager mFragmentManager, List<Genealogy> data) {
        this.mContext = mContext;
        this.genealogies = data;
        this.mFragmentManager = mFragmentManager;
    }

    private Genealogy updatedGenealogy;
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_genealogy, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final int genealogyId = genealogies.get(position).getId();

        final String genealogyName = genealogies.get(position).getName();
        final String genealogyHistory = genealogies.get(position).getHistory();
        final String genealogyOwner = genealogies.get(position).getOwner();
        final int genealogyBranch = genealogies.get(position).getBranch();
        final Date genealogyDate = genealogies.get(position).getDate();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final String date = formatter.format(genealogyDate);

        holder.txtGenealogyName.setText(genealogyName);
        holder.txtGenealogyBranches.setText(String.valueOf(genealogyBranch));
        holder.txtGenealogyDate.setText(date);

        final int role = genealogies.get(position).getRole();
        switch (role){
            case Constants.ROLE.OWNER_ROLE:
                holder.imgRole.setImageResource(R.drawable.ic_admin);
                break;
            case Constants.ROLE.EDITOR_ROLE:
                holder.imgRole.setImageResource(R.drawable.ic_mod);
                break;
            case Constants.ROLE.MEMBER_ROLE:
                holder.imgRole.setImageResource(R.drawable.ic_member);
                break;
        }

        final Genealogy genealogy = genealogies.get(position);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailGenealogyFragment mFragment = new DetailGenealogyFragment();
                Bundle bundle = new Bundle();
                if (updatedGenealogy != null){
                    bundle.putSerializable("genealogy", updatedGenealogy);
                } else {
                    bundle.putSerializable("genealogy", genealogy);
                }
                bundle.putSerializable("genealogyList", (Serializable) genealogies);
                mFragment.setArguments(bundle);
                if(mContext instanceof HomeActivity){
                    ((HomeActivity) mContext).updateTitleBar("Genealogy Information");
                }
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.search_frame);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genealogies.size();
    }


    public void updateRcvGenealogy(List<Genealogy> genealogyList){
        genealogies.clear();
        genealogies.addAll(genealogyList);
        notifyDataSetChanged();
    }

    public void disable(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof RecyclerViewItemGenealogyAdapter.RecyclerViewHolder) {
            ((RecyclerViewHolder) viewHolder).viewBackground.setEnabled(false);
        }
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtGenealogyName;
        TextView txtGenealogyBranches;
        TextView txtGenealogyDate;
        ImageView imgRole;
        FrameLayout line;
        RelativeLayout viewBackground, viewForeground;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtGenealogyName = (TextView) itemView.findViewById(R.id.txtGenealogyName);
            txtGenealogyBranches = (TextView) itemView.findViewById(R.id.txtGenealogyBranches);
            txtGenealogyDate = (TextView) itemView.findViewById(R.id.txtGenealogyDate);
            imgRole = (ImageView) itemView.findViewById(R.id.imgRole);
            line = (FrameLayout) itemView.findViewById(R.id.line);
            viewBackground = (RelativeLayout)itemView.findViewById(R.id.view_background);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.view_foreground);
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
