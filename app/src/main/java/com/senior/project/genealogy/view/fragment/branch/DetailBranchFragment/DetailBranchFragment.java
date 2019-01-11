package com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.DetailEventBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment.DetailInformationBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment.DetailMemberRequestBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.adapter.SectionsPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBranchFragment extends Fragment implements DetailBranchFragmentView {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private Branch branch;

    private DetailInformationBranchFragment mInformationBranchFrg;
    private DetailMemberBranchFragment mMemberBranchFrg;
    private DetailMemberRequestBranchFragment mMemberRequestBranchFrg;
    private DetailEventBranchFragment mDetailEventBranchFragment;

    public DetailBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_detail, container, false);
        ButterKnife.bind(this, view);
        branch = (Branch) getArguments().getSerializable("branch");
        if (mInformationBranchFrg == null)
            mInformationBranchFrg = new DetailInformationBranchFragment();
        if (mDetailEventBranchFragment == null)
            mDetailEventBranchFragment = new DetailEventBranchFragment();
        if (mMemberBranchFrg == null)
            mMemberBranchFrg = new DetailMemberBranchFragment();

        if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE){
            if (mMemberRequestBranchFrg == null)
                mMemberRequestBranchFrg = new DetailMemberRequestBranchFragment();
            mMemberRequestBranchFrg.attackInterface(new DetailMemberRequestBranchFragment.RequestMemberInterface() {
                @Override
                public void sendDataToListMember(User member) {
                    updateMember(member);
                }
            });
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            tv1.setScaleY(-1);
            TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
            tv2.setScaleY(-1);
            TextView tv3 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(2)).getChildAt(1));
            tv3.setScaleY(-1);
            TextView tv4 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(3)).getChildAt(1));
            tv4.setScaleY(-1);
        } else {
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            tv1.setScaleY(-1);
            TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
            tv2.setScaleY(-1);
            TextView tv3 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(2)).getChildAt(1));
            tv3.setScaleY(-1);
        }
        return view;
    }

    public void updateMember(User user){
        mMemberBranchFrg.updateMember(user);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter.TitleStringUtils titleStringUtils = new SectionsPageAdapter.TitleStringUtils(getActivity());
        SectionsPageAdapter adapter;
        int numPage;
        List<Fragment> arrListFrg = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("branch", branch);
        if (branch.getRole()== Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE) {
            arrListFrg.add(mInformationBranchFrg);
            arrListFrg.add(mDetailEventBranchFragment);
            arrListFrg.add(mMemberBranchFrg);
            arrListFrg.add(mMemberRequestBranchFrg);
            mInformationBranchFrg.setArguments(bundle);
            mDetailEventBranchFragment.setArguments(bundle);
            mMemberBranchFrg.setArguments(bundle);
            mMemberRequestBranchFrg.setArguments(bundle);
            numPage = titleStringUtils.gettitlesAsRoleIsAdmin().length;
            adapter = new SectionsPageAdapter(this.getChildFragmentManager(), titleStringUtils.gettitlesAsRoleIsAdmin(), arrListFrg);
        } else {
            arrListFrg.add(mInformationBranchFrg);
            arrListFrg.add(mDetailEventBranchFragment);
            arrListFrg.add(mMemberBranchFrg);
            mInformationBranchFrg.setArguments(bundle);
            mDetailEventBranchFragment.setArguments(bundle);
            mMemberBranchFrg.setArguments(bundle);
            numPage = titleStringUtils.gettitlesAsRoleIsNormal().length;
            adapter = new SectionsPageAdapter(this.getChildFragmentManager(), titleStringUtils.gettitlesAsRoleIsNormal(), arrListFrg);
        }        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(numPage);
    }

    @Override
    public void onDestroyView() {
        mUpdateBranchInterface.refreshBranches();
        super.onDestroyView();
    }

    public interface UpdateBranchInterface{
        void refreshBranches();
    }

    public UpdateBranchInterface mUpdateBranchInterface;

    public void attachInterface(UpdateBranchInterface updateBranchInterface){
        mUpdateBranchInterface = updateBranchInterface;
    }
}
