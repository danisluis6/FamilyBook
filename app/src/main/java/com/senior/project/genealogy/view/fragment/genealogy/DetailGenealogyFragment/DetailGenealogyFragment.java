package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Config;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment.BranchFragment;
import com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment.UpdateGenealogyFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailGenealogyFragment extends Fragment implements DetailGenealogyFragmentView{

    @BindView(R.id.txtGenealogyName)
    TextView txtGenealogyName;

    @BindView(R.id.txtGenealogyOwner)
    TextView txtGenealogyOwner;

    @BindView(R.id.txtGenealogyDate)
    TextView txtGenealogyDate;

    @BindView(R.id.txtBranches)
    TextView txtBranches;

    @BindView(R.id.txtHistory)
    TextView txtHistory;

    @BindView(R.id.btnEditGenealogy)
    FloatingActionButton btnEditGenealogy;

    private DetailGenealogyFragmentPresenterImpl detailGenealogyFragmentPresenter;
    private ProgressDialog mProgressDialog;
    private Genealogy genealogy;

    public DetailGenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_detail, container, false);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_view_genealogy));
        genealogy = (Genealogy) getArguments().getSerializable("genealogy");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if (genealogy.getRole()!= Constants.ROLE.OWNER_ROLE){
            btnEditGenealogy.setVisibility(View.GONE);
        }
        showGenealogy(genealogy);
        return view;
    }

    /**
     * Interface communicate DetailFragment vs HomeActivity
     * Back -> Manager (DetailFragment)
     * Manager.getCould > 0 => pop
     *
     * interface x => x.isNestedFragment() [Detail]
     * => Interface(HomeActivity) => Detail
     */
    public interface DetailGenealogyInterface {
        boolean isNestedFrragment();
    }

    private DetailGenealogyInterface mDetailGenealogyInterface;

    @OnClick({R.id.btnEditGenealogy, R.id.btnBranch})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch(view.getId())
        {
            case R.id.btnEditGenealogy:
                UpdateGenealogyFragment mFragment = new UpdateGenealogyFragment();
                bundle.putSerializable("genealogy", genealogy);
                mFragment.setArguments(bundle);
                mFragment.attachInterface(new UpdateGenealogyFragment.UpdateGenealogyInterface() {
                    @Override
                    public void sendDataUpdateToGenealogy(Genealogy genealogy) {
                        updateGenealogy(genealogy);
                    }
                });
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.genealogy_detail_frame);
                break;

            case R.id.btnBranch:
                BranchFragment fragment = new BranchFragment();
                bundle.putSerializable("genealogy", genealogy);
                fragment.setArguments(bundle);
                pushFragment(HomeActivity.PushFrgType.ADD, fragment, fragment.getTag(), R.id.genealogy_detail_frame);
                break;
        }
    }


    public void updateGenealogy(Genealogy genealogy){
        txtGenealogyName.setText(genealogy.getName());
        txtHistory.setText(genealogy.getHistory());
    }

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
    public void showGenealogy(Genealogy genealogy) {
        txtGenealogyName.setText(genealogy.getName());
        txtHistory.setText(genealogy.getHistory());
        txtBranches.setText(genealogy.getBranch().toString());
        txtGenealogyOwner.setText(genealogy.getOwner());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String genealogyDate = formatter.format(genealogy.getDate());
        txtGenealogyDate.setText(genealogyDate);
    }

    @Override
    public void onDestroyView() {
        mUpdateGenealogyListInterface.refreshGenealogies();
        super.onDestroyView();
    }

    @Override
    public void closeFragment() {
    }

    public interface UpdateGenealogyListInterface{
        void refreshGenealogies();
    }

    public UpdateGenealogyListInterface mUpdateGenealogyListInterface;

    public void attachInterface(UpdateGenealogyListInterface updateGenealogyListInterface){
        mUpdateGenealogyListInterface = updateGenealogyListInterface;
    }
}
