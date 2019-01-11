package com.senior.project.genealogy.view.fragment.search.BranchSearchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.fragment.search.Adapter.RecyclerViewItemBranchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;


public class BranchSearchFragment extends Fragment implements BranchSearchFragmentView{

    @BindView(R.id.edtSearchBranch)
    EditText edtSearchBranch;

    @BindView(R.id.txtNotFoundBranch)
    TextView txtNotFoundBranch;

    @BindView(R.id.rcvBranch)
    RecyclerView rcvBranch;

    private ProgressDialog mProgressDialog;
    private BranchSearchFragmentPresenter branchSearchFragmentPresenter;
    private String token;
    private List<Branch> branchList;
    private RecyclerViewItemBranchAdapter mRcvAdapter;

    public BranchSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_branch, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        branchSearchFragmentPresenter = new BranchSearchFragmentPresenterImpl(this);
        initRcvBranch();
        
        edtSearchBranch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    branchSearchFragmentPresenter.searchBranchByName(new Search(edtSearchBranch.getText().toString()), token);
                    Utils.hiddenKeyBoard(getActivity());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void showBranch(List<Branch> branchList) {
        if(branchList.size() == 0){
            txtNotFoundBranch.setVisibility(View.VISIBLE);
            mRcvAdapter.updateRcvBranch(branchList);
        }
        else {
            txtNotFoundBranch.setVisibility(View.GONE);
            mRcvAdapter.updateRcvBranch(branchList);
        }
    }

    public void initRcvBranch(){
        branchList = new ArrayList<>();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemBranchAdapter(getActivity(), fragmentManager, branchList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvBranch.setLayoutManager(layoutManager);
        rcvBranch.setAdapter(mRcvAdapter);
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
}
