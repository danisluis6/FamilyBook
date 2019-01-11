package com.senior.project.genealogy.view.fragment.branch.DetailChatBranchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;

import butterknife.ButterKnife;

public class DetailChatBranchFragment extends Fragment {

    public DetailChatBranchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
