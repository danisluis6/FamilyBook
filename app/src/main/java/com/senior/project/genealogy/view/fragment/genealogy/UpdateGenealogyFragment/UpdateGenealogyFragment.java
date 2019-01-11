package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateGenealogyFragment extends Fragment implements UpdateGenealogyFragmentView{

    @BindView(R.id.edtGenealogyName)
    EditText edtGenealogyName;

    @BindView(R.id.edtGenealogyHistory)
    EditText edtGenealogyHistory;

    @BindView(R.id.txtGenealogyOwner)
    TextView txtGenealogyOwner;

    @BindView(R.id.txtGenealogyDate)
    TextView txtGenealogyDate;

    @BindView(R.id.txtBranches)
    TextView txtBranches;

    @BindView(R.id.btnDoneEdit)
    FloatingActionButton btnDoneEdit;

    private UpdateGenealogyFragmentPresenterImpl updateGenealogyFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private Genealogy genealogy;
    public UpdateGenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_update, container, false);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_update_genealogy));
        genealogy = (Genealogy) getArguments().getSerializable("genealogy");
        showGenealogy(genealogy);
        return view;
    }

    @OnClick(R.id.btnDoneEdit)
    public void onClick()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        genealogy.setName(edtGenealogyName.getText().toString());
        genealogy.setHistory(edtGenealogyHistory.getText().toString());



        Genealogy updatedGenealogy = genealogy;
        updatedGenealogy.setDate(null);

        updateGenealogyFragmentPresenterImpl = new UpdateGenealogyFragmentPresenterImpl(this);
        updateGenealogyFragmentPresenterImpl.updateGenealogy(updatedGenealogy, token);
    }

    @Override
    public void showGenealogy(Genealogy genealogy) {
        edtGenealogyName.setText(genealogy.getName());
        edtGenealogyHistory.setText(genealogy.getHistory());
        txtGenealogyOwner.setText(genealogy.getOwner());
        txtBranches.setText(genealogy.getBranch().toString());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String genealogyDate = formatter.format(genealogy.getDate());
        txtGenealogyDate.setText(genealogyDate);
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
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closeFragment(Genealogy genealogy) {
        try {
            String sDate1 = txtGenealogyDate.getText().toString();
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            genealogy.setDate(date1);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        mUpdateGenealogyInterface.sendDataUpdateToGenealogy(genealogy);
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_view_genealogy));
        }
        getActivity().onBackPressed();
    }

    public interface UpdateGenealogyInterface{
        void sendDataUpdateToGenealogy(Genealogy genealogy);
    }

    public UpdateGenealogyInterface mUpdateGenealogyInterface;

    public void attachInterface(UpdateGenealogyInterface updateGenealogyInterface){
        mUpdateGenealogyInterface = updateGenealogyInterface;
    }
}
