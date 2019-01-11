package com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.senior.project.genealogy.view.fragment.familyTree.adapter.RecyclerItemBranchTouchHelper;
import com.senior.project.genealogy.view.fragment.familyTree.adapter.RecyclerViewItemBranchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyTreeFragment extends Fragment implements FamilyTreeFragmentView,RecyclerItemBranchTouchHelper.RecyclerItemTouchHelperListener{

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

    private FamilyTreeFragmentPresenterImpl familyTreeFragmentPresenter;
    private ProgressDialog mProgressDialog;
    private String token;
    private Context mContext;

    public FamilyTreeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_family_tree));
        familyTreeFragmentPresenter = new FamilyTreeFragmentPresenterImpl(this);
        familyTreeFragmentPresenter.getGenealogiesByUsername(token);

        return view;
    }

    public void addItemsOnSpinnerGenealogy(List<Genealogy> genealogyList) {
        if(genealogyList == null) {
            recyclerViewBranch.setVisibility(View.GONE);
            spGenealogy.setVisibility(View.GONE);
            txtNotice.setVisibility(View.VISIBLE);
        } else {
            ArrayAdapter<Genealogy> dataAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, genealogyList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGenealogy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Genealogy genealogy = (Genealogy) spGenealogy.getSelectedItem();
                    showBranch(genealogy.getBranchList());
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spGenealogy.setAdapter(dataAdapter);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void closeProgressDialog() {
        if (mContext instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) mContext;
            if (!homeActivity.isFinishing()) {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        }
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
        mRcvAdapter = new RecyclerViewItemBranchAdapter(getActivity(), fragmentManager, branches, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewBranch.setLayoutManager(layoutManager);
        recyclerViewBranch.setAdapter(mRcvAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemBranchTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewBranch);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewItemBranchAdapter.RecyclerViewHolder) {
            showAlertDialog("Delete", "Are you sure?", "Delete", "Cancel", viewHolder);
        }
    }

    public void showAlertDialog(String title, String message, String positive, String negative, final RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int branchId = branches.get(viewHolder.getAdapterPosition()).getId();
                familyTreeFragmentPresenter.deleteBranch(branchId, token, viewHolder);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mRcvAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void deleteItemBranch(RecyclerView.ViewHolder viewHolder) {
        mRcvAdapter.removeItem(viewHolder.getAdapterPosition());
        if (mRcvAdapter.getItemCount() == 0){
            txtNoticeBranch.setVisibility(View.VISIBLE);
        }
    }

    public void refreshListFamilyTree() {
        familyTreeFragmentPresenter = new FamilyTreeFragmentPresenterImpl(this);
        familyTreeFragmentPresenter.getGenealogiesByUsername(token);
    }
}
