package com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateBranchFragment extends Fragment implements CreateBranchFragmentView {

    @BindView(R.id.btnCreateBranch)
    Button btnCreateBranch;

    @BindView(R.id.edtBranchName)
    EditText edtBranchName;

    @BindView(R.id.txtGenealogyName)
    TextView txtGenealogyName;

    @BindView(R.id.edtDescription)
    EditText edtDescription;

    private CreateBranchFragmentPresenterImpl createBranchFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;

    public CreateBranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_create, container, false);
        createBranchFragmentPresenterImpl = new CreateBranchFragmentPresenterImpl(this);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_create_branch));
        ButterKnife.bind(this, view);
        txtGenealogyName.setText(getArguments().getString("genealogyName"));
        return view;
    }

    @OnClick(R.id.btnCreateBranch)
    public void onClick()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Branch branch = new Branch(edtBranchName.getText().toString(), edtDescription.getText().toString(), getArguments().getInt("genealogyId"));
        createBranchFragmentPresenterImpl.createBranch(branch, token);
    }

    @OnTextChanged(R.id.edtBranchName)
    protected void onTextChanged() {

        String branchName = edtBranchName.getText().toString().trim();
        if (branchName.isEmpty()){
            btnCreateBranch.setBackgroundResource(R.drawable.radius_button_disable);
            btnCreateBranch.setEnabled(false);
        }
        else {
            btnCreateBranch.setBackgroundResource(R.drawable.radius_button);
            btnCreateBranch.setEnabled(true);
        }

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }


    public ProgressDialog initProgressDialog(){
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
    public void closeFragment(List<Branch> branchList) {
        mCreateBranchInterface.sendDataToListBranch(branchList.get(0));
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).updateTitleBar("Branch");
        }
        getActivity().onBackPressed();
    }

    public interface CreateBranchInterface{
        void sendDataToListBranch(Branch branch);
    }

    public CreateBranchInterface mCreateBranchInterface;

    public void attackInterface(CreateBranchInterface createBranchInterface){
        mCreateBranchInterface = createBranchInterface;
    }
}
