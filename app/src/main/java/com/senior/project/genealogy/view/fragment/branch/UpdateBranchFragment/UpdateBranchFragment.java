package com.senior.project.genealogy.view.fragment.branch.UpdateBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.familyTree.MapFragment.MapFragment;
import com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment.UpdateGenealogyFragmentPresenterImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBranchFragment extends Fragment implements UpdateBranchFragmentView {

    @BindView(R.id.edtBranchName)
    TextView edtBranchName;

    @BindView(R.id.txtBranchDate)
    TextView txtBranchDate;

    @BindView(R.id.txtPeople)
    TextView txtPeople;

    @BindView(R.id.btnDoneEditBranch)
    FloatingActionButton btnDoneEditBranch;

    @BindView(R.id.edtDescription)
    TextView edtDescription;

    private UpdateBranchFragmentPresenterImpl updatedBranchFragmentPresenterImpl;
    private Branch branch;
    private ProgressDialog mProgressDialog;

    public UpdateBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_information_update, container, false);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_update_branch));
        branch = (Branch) getArguments().getSerializable("branch");
        showBranch(branch);
        return view;
    }

    @OnClick(R.id.btnDoneEditBranch)
    public void onClick() { SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        branch.setName(edtBranchName.getText().toString());
        branch.setDescription(edtDescription.getText().toString());

        Branch updatedBranch = branch;
        updatedBranch.setDate(null);

        updatedBranchFragmentPresenterImpl = new UpdateBranchFragmentPresenterImpl(this);
        updatedBranchFragmentPresenterImpl.updateBranch(updatedBranch, token);
    }

    @Override
    public void showBranch(Branch branch) {
        edtBranchName.setText(branch.getName());
        edtDescription.setText(branch.getDescription());
        txtPeople.setText(branch.getMember().toString());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String branchDate = formatter.format(branch.getDate());
        txtBranchDate.setText(branchDate);
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
    public void closeFragment(Branch branch) {
        try {
            String sDate1 = txtBranchDate.getText().toString();
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            branch.setDate(date1);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        mUpdateBranchInterface.sendDataUpdateToBranch(branch);
        getActivity().onBackPressed();
    }


    public interface UpdateBranchInterface{
        void sendDataUpdateToBranch(Branch branch);
    }

    public UpdateBranchInterface mUpdateBranchInterface;

    public void attachInterface(UpdateBranchInterface updateBranchInterface){
        mUpdateBranchInterface = updateBranchInterface;
    }
}
