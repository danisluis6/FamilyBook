package com.senior.project.genealogy.view.fragment.search.ShowBranchFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.search.Adapter.RecyclerViewItemBranchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchFragment extends Fragment implements BranchFragmentView{

    @BindView(R.id.btnCreateBranch)
    FloatingActionButton btnCreateBranch;

    @BindView(R.id.recyclerViewBranch)
    RecyclerView recyclerViewBranch;

    @BindView(R.id.spGenealogy)
    Spinner spGenealogy;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    @BindView(R.id.txtNoticeBranch)
    TextView txtNoticeBranch;

    RecyclerViewItemBranchAdapter mRcvAdapter;
    List<Branch> branches;

    private ProgressDialog mProgressDialog;
    private String token;
    private Genealogy genealogy;
    private List<Genealogy> genealogyList;
    public BranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, container, false);

        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_branches));
        genealogyList = (List<Genealogy>) getArguments().getSerializable("genealogyList");
        addItemsOnSpinnerGenealogy(genealogyList);

        return view;
    }

    public void addItemsOnSpinnerGenealogy(List<Genealogy> genealogyList) {
        if(genealogyList == null){
            btnCreateBranch.setVisibility(View.GONE);
            recyclerViewBranch.setVisibility(View.GONE);
            spGenealogy.setVisibility(View.GONE);
            txtNotice.setVisibility(View.VISIBLE);
        } else {
            ArrayAdapter<Genealogy> dataAdapter = new ArrayAdapter<Genealogy>(getContext(), R.layout.spinner_item, genealogyList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spGenealogy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    Genealogy genealogy = (Genealogy) spGenealogy.getSelectedItem();
                    if(genealogy.getRole() == Constants.ROLE.OWNER_ROLE){
                        btnCreateBranch.setVisibility(View.VISIBLE);
                    } else {
                        btnCreateBranch.setVisibility(View.GONE);
                    }
                    showBranch(genealogy.getBranchList());
                    //branchFragmentPresenterImpl.getBranchesByGenealogyId(token, genealogy.getId());
                }
                public void onNothingSelected(AdapterView<?> parent)
                {
                }
            });

            spGenealogy.setAdapter(dataAdapter);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                genealogy = (Genealogy) getArguments().getSerializable("genealogy");
                for (Genealogy item : genealogyList){
                    if(item.getId() == genealogy.getId()){
                        spGenealogy.setSelection(dataAdapter.getPosition(item));
                    }
                }
            }
        }
    }

//    @OnClick(R.id.btnCreateBranch)
//    public void onClick() {
//        CreateBranchFragment mFragment = new CreateBranchFragment();
//        Genealogy genealogy = (Genealogy) spGenealogy.getSelectedItem();
//        Bundle bundle = new Bundle();
//        bundle.putInt("genealogyId", genealogy.getId());
//        bundle.putString("genealogyName", genealogy.getName());
//        mFragment.setArguments(bundle);
//
//        mFragment.attackInterface(new CreateBranchFragment.CreateBranchInterface() {
//            @Override
//            public void sendDataToListBranch(Branch branch) {
//                if(txtNoticeBranch.getVisibility() == View.VISIBLE){
//                    txtNoticeBranch.setVisibility(View.GONE);
//                }
//                mRcvAdapter.updateBranch(branch);
//            }
//        });
//        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.branch_frame);
//    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
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
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public ProgressDialog initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void showBranch(List<Branch> branchList) {
        branches = new ArrayList<>();

        if(branchList.size() == 0){
            txtNoticeBranch.setVisibility(View.VISIBLE);
        }
        else {
            txtNoticeBranch.setVisibility(View.GONE);
            branches.addAll(branchList);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemBranchAdapter(getActivity(), fragmentManager, branches);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewBranch.setLayoutManager(layoutManager);
        recyclerViewBranch.setAdapter(mRcvAdapter);
    }

}
