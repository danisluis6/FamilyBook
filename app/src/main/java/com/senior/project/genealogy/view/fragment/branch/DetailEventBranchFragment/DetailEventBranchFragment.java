package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.adapter.EventAdapter;
import com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.dialog.DialogEventFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailEventBranchFragment extends Fragment implements DetailEventBranchView {

    @BindView(R.id.btnCreateEvent)
    Button btnCreateEvent;

    @BindView(R.id.rcvEvent)
    RecyclerView rcvEvent;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    private Branch branch;
    private ProgressDialog mProgressDialog;
    private DetailEventBranchPresenter mDetailEventBranchPresenter;
    private List<Event> events;
    private EventAdapter mRcvAdapter;
    private String token;
    public DetailEventBranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_event, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        mDetailEventBranchPresenter = new DetailEventBranchPresenterImpl(this);
        branch = (Branch) getArguments().getSerializable("branch");
        if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE){
            btnCreateEvent.setVisibility(View.VISIBLE);
        }else {
            btnCreateEvent.setVisibility(View.GONE);
        }
        mDetailEventBranchPresenter.getCreatedEvent(branch.getId(), token);
        return view;
    }

    @OnClick(R.id.btnCreateEvent)
    public void onClick() {
        if (Utils.isDoubleClick()) return;
        final DialogEventFragment dialogEventFragment = DialogEventFragment.newInstance();
        dialogEventFragment.show(getActivity().getSupportFragmentManager(), null);
        dialogEventFragment.attackInterface(new DialogEventFragment.DialogEventInterface() {
            @Override
            public void sendDataToFrg(Event event) {
                dialogEventFragment.dismiss();
                event.setBranchId(branch.getId());

                mDetailEventBranchPresenter.pushEvent(event, token);
            }
        });
    }

    @Override
    public void showListCreatedEvent(List<Event> eventList) {
        events = new ArrayList<>();
        if(eventList == null){
            txtNotice.setVisibility(View.VISIBLE);
        }
        else {
            events.addAll(eventList);
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new EventAdapter(getActivity(), fragmentManager, events);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvEvent.setLayoutManager(layoutManager);
        rcvEvent.setAdapter(mRcvAdapter);
    }

    @Override
    public void addEventToList(Event event) {
        mRcvAdapter.addEvent(event);
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (!getActivity().isFinishing() && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }
}
