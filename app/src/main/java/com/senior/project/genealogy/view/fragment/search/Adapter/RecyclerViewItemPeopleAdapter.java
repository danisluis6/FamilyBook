package com.senior.project.genealogy.view.fragment.search.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.search.MemberSearchFragment.PeopleSearchFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewItemPeopleAdapter extends RecyclerView.Adapter<RecyclerViewItemPeopleAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private PeopleSearchFragment mPeopleSearchFragment;
    private List<People> mPeople;

    public RecyclerViewItemPeopleAdapter(Context mContext, FragmentManager mFragmentManager, List<People> mPeople, PeopleSearchFragment mPeopleSearchFragment) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.mPeople = mPeople;
        this.mPeopleSearchFragment = mPeopleSearchFragment;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_people, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String peopleAva = mPeople.get(position).getImage();
        final String peopleName = mPeople.get(position).getName();
        final String peopleNickname = mPeople.get(position).getNickname();
        final String peopleBirthday = mPeople.get(position).getBirthday();
        final String peopleDeathday = mPeople.get(position).getDeathDay();
        final String peopleAddress = mPeople.get(position).getAddress();
        final String peopleDescription = mPeople.get(position).getDescription();

        if(!Constants.EMPTY_STRING.equals(peopleAva)){
//            holder.civAva.setImageResource(peopleAva);
        }
        holder.txtName.setText(peopleName);
        holder.txtNickname.setText(peopleNickname);
        holder.txtBirthday.setText(peopleBirthday);
        holder.txtDeathday.setText(peopleDeathday);
        holder.txtAddress.setText(peopleAddress);
        holder.txtDescription.setText(peopleDescription);

        if(mPeople.get(position).getGender() != null){
            final int gender = mPeople.get(position).getGender();
            switch (gender){
                case 0:
                    holder.imgGenger.setImageResource(R.drawable.ic_female);
                    break;
                case 1:
                    holder.imgGenger.setImageResource(R.drawable.ic_male);
                    break;
            }
        }
        final People people = mPeople.get(position);

        holder.linePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeopleSearchFragment.getBranchById(people.getBranchId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    public void updateRcvPeople(List<People> peopleList){
        mPeople.clear();
        mPeople.addAll(peopleList);
        Log.i("TAG", mPeople.toString());
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAva;
        TextView txtName;
        ImageView imgGenger;
        TextView txtNickname;
        TextView txtBirthday;
        TextView txtDeathday;
        TextView txtAddress;
        TextView txtDescription;
        FrameLayout linePeople;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            civAva = (CircleImageView) itemView.findViewById(R.id.civAva);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgGenger = (ImageView) itemView.findViewById(R.id.imgGenger);
            txtNickname = (TextView) itemView.findViewById(R.id.txtNickname);
            txtBirthday = (TextView) itemView.findViewById(R.id.txtBirthday);
            txtDeathday = (TextView)itemView.findViewById(R.id.txtDeathday);
            txtAddress = (TextView)itemView.findViewById(R.id.txtAddress);
            txtDescription = (TextView)itemView.findViewById(R.id.txtDescription);
            linePeople = (FrameLayout) itemView.findViewById(R.id.linePeople);
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
