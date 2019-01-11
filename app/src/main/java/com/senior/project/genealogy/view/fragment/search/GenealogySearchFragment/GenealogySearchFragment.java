package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.search.Adapter.RecyclerViewItemGenealogyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;

public class GenealogySearchFragment extends Fragment implements GenealogySearchFragmentView{

    @BindView(R.id.edtSearchGenealogy)
    EditText edtSearchGenealogy;

    @BindView(R.id.txtNotFoundGenealogy)
    TextView txtNotFoundGenealogy;

    @BindView(R.id.rcvGenealogy)
    RecyclerView rcvGenealogy;

    private ProgressDialog mProgressDialog;
    private GenealogySearchFragmentPresenter genealogySearchFragmentPresenter;
    private String token;
    private List<Genealogy> genealogyList;
    private RecyclerViewItemGenealogyAdapter mRcvAdapter;

    public GenealogySearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_genealogy, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        genealogySearchFragmentPresenter = new GenealogySearchFragmentPresenterImpl(this);
        initRcvGenealogy();

        edtSearchGenealogy.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    genealogySearchFragmentPresenter.searchGenealogyByName(new Search(edtSearchGenealogy.getText().toString()), token);
                    Utils.hiddenKeyBoard(getActivity());
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void showGenealogy(List<Genealogy> genealogyList) {
        if(genealogyList.size() == 0){
            txtNotFoundGenealogy.setVisibility(View.VISIBLE);
            mRcvAdapter.updateRcvGenealogy(genealogyList);
        }
        else {
            txtNotFoundGenealogy.setVisibility(View.GONE);
            mRcvAdapter.updateRcvGenealogy(genealogyList);
        }
    }

    public void initRcvGenealogy(){
        genealogyList = new ArrayList<>();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemGenealogyAdapter(getActivity(), fragmentManager, genealogyList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvGenealogy.setLayoutManager(layoutManager);
        rcvGenealogy.setAdapter(mRcvAdapter);
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            if (getActivity() instanceof HomeActivity) {
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
            }
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
}
