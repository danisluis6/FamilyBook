package com.senior.project.genealogy.view.fragment.search.Adapter;

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

        public String[] gettitlesSearchType() {
            return new String[]{mContext.getResources().getString(R.string.tab_title_search_genealogy), mContext.getResources().getString(R.string.tab_title_search_branch), mContext.getResources().getString(R.string.tab_title_search_people)};
        }
    }
}
