package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

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
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateGenealogyFragment extends Fragment implements CreateGenealogyFragmentView{

    @BindView(R.id.btnCreate)
    Button btnCreate;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtHistory)
    EditText edtHistory;

    private CreateGenealogyFragmentPresenterImpl createGenealogyFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;

    public CreateGenealogyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_create, container, false);
        createGenealogyFragmentPresenterImpl = new CreateGenealogyFragmentPresenterImpl(this);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_create_genealogy));
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnCreate)
    public void onClick()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Genealogy genealogy = new Genealogy(edtName.getText().toString(), edtHistory.getText().toString());
        createGenealogyFragmentPresenterImpl.createGenealogy(genealogy, token);
    }

    @OnTextChanged(R.id.edtName)
    protected void onTextChanged() {

        String genealogyName = edtName.getText().toString().trim();

        if (genealogyName.isEmpty()){
            btnCreate.setBackgroundResource(R.drawable.radius_button_disable);
            btnCreate.setEnabled(false);
        }
        else {
            btnCreate.setBackgroundResource(R.drawable.radius_button);
            btnCreate.setEnabled(true);
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
    public void closeFragment(List<Genealogy> genealogyList) {
        mCreateGenealogyInterface.sendDataToListGenealogy(genealogyList.get(0));
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_my_genealogy));
        }
        getActivity().onBackPressed();
    }

    public interface CreateGenealogyInterface{
        void sendDataToListGenealogy(Genealogy genealogy);
    }

    public CreateGenealogyInterface mCreateGenealogyInterface;

    public void attackInterface(CreateGenealogyInterface createGenealogyInterface){
        mCreateGenealogyInterface = createGenealogyInterface;
    }


}
