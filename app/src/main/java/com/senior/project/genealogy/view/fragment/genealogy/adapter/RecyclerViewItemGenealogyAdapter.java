package com.senior.project.genealogy.view.fragment.genealogy.adapter;

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
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragment;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewItemGenealogyAdapter extends RecyclerView.Adapter<RecyclerViewItemGenealogyAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private GenealogyFragment  mGenealogyFragment;
    private List<Genealogy> data = new ArrayList<>();

    public RecyclerViewItemGenealogyAdapter(Context mContext, FragmentManager mFragmentManager, List<Genealogy> data, GenealogyFragment genealogyFragment) {
        this.mContext = mContext;
        this.data = data;
        this.mFragmentManager = mFragmentManager;
        this.mGenealogyFragment = genealogyFragment;
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
        final int genealogyId = data.get(position).getId();

        final String genealogyName = data.get(position).getName();
        final String genealogyHistory = data.get(position).getHistory();
        final String genealogyOwner = data.get(position).getOwner();
        final int genealogyBranch = data.get(position).getBranch();
        final Date genealogyDate = data.get(position).getDate();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final String date = formatter.format(genealogyDate);

        holder.txtGenealogyName.setText(genealogyName);
        holder.txtGenealogyBranches.setText(String.valueOf(genealogyBranch));
        holder.txtGenealogyDate.setText(date);

        final int role = data.get(position).getRole();
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

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailGenealogyFragment mFragment = new DetailGenealogyFragment();
                Bundle bundle = new Bundle();
                final Genealogy genealogy = new Genealogy(genealogyId, genealogyName, genealogyHistory, genealogyOwner, genealogyDate, genealogyBranch, role);
                if (updatedGenealogy != null){
                    bundle.putSerializable("genealogy", updatedGenealogy);
                } else {
                    bundle.putSerializable("genealogy", genealogy);
                }
                mFragment.setArguments(bundle);
                if(mContext instanceof HomeActivity){
                    ((HomeActivity) mContext).updateTitleBar("Genealogy Information");
                }
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.genealogy_frame);
                mFragment.attachInterface(new DetailGenealogyFragment.UpdateGenealogyListInterface() {
                    @Override
                    public void refreshGenealogies() {
                        mGenealogyFragment.refreshListGenealogies();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void updateGenealogy(Genealogy genealogy) {
        data.add(genealogy);
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
