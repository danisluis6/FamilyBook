package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.senior.project.genealogy.R;

import java.util.List;

import javax.inject.Inject;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {

    private String[] titles;
    private List<Fragment> arrListFrg;

    public SectionsPageAdapter(FragmentManager fragmentManager, String[] titleStringUtils, List<Fragment> arrListFrg) {
        super(fragmentManager);
        titles = titleStringUtils;
        this.arrListFrg = arrListFrg;
    }

    @Override
    public Fragment getItem(int position) {
        return arrListFrg.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public static class TitleStringUtils {

        private Context mContext;

        @Inject
        public TitleStringUtils(Context context) {
            mContext = context;
        }

        public String[] gettitlesAsRoleIsAdmin() {
            return new String[]{mContext.getResources().getString(R.string.tab_title_detail), mContext.getString(R.string.tab_title_event), mContext.getString(R.string.tab_title_member), mContext.getResources().getString(R.string.tab_title_request)};
        }

        public String[] gettitlesAsRoleIsNormal() {
            return new String[]{mContext.getResources().getString(R.string.tab_title_detail), mContext.getString(R.string.tab_title_event), mContext.getString(R.string.tab_title_member)};
        }
    }
}
