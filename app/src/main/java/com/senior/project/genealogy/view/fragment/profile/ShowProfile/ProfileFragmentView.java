package com.senior.project.genealogy.view.fragment.profile.ShowProfile;

import android.support.v7.widget.RecyclerView;

import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.User;

import java.util.List;

public interface ProfileFragmentView {
    void showProfile(User user);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
}
