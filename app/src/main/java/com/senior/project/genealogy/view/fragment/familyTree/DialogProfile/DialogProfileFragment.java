package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;
import com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile.UpdateDialogNodeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogProfileFragment extends DialogFragment implements DialogProfileFragmentView {

    @BindView(R.id.btnMenu)
    FloatingActionMenu btnMenu;

    @BindView(R.id.btnAddNode)
    FloatingActionButton btnAddNode;

    @BindView(R.id.btnRelative)
    FloatingActionButton btnRelative;

    @BindView(R.id.tvProfile)
    TextView tvProfile;

    @BindView(R.id.imgGenger)
    ImageView imgGenger;

    @BindView(R.id.txtFullname)
    TextView txtFullname;

    @BindView(R.id.txtNickname)
    TextView txtNickname;

    @BindView(R.id.txtBirthday)
    TextView txtBirthday;

    @BindView(R.id.txtDeathdayTitle)
    TextView txtDeathdayTitle;

    @BindView(R.id.txtDeathday)
    TextView txtDeathday;

    @BindView(R.id.txtAddress)
    TextView txtAddress;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    private ProgressDialog mProgressDialog;
    private DialogProfileFragmentPresenterImpl dialogProfileFragmentPresenterImpl;
    private People people;
    private People updatedPeople;
    private int nodeType;

    public DialogProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_profile, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        people = (People) getArguments().getSerializable("people");
        nodeType = getArguments().getInt("nodeType");
        if(nodeType == Constants.NODE_TYPE.PARTNER){
            btnAddNode.setVisibility(View.GONE);
            btnRelative.setVisibility(View.GONE);
        }
        showInformation(people);
        return view;
    }

    public static DialogProfileFragment newInstance(People people, int branchId, int nodeType) {
        DialogProfileFragment dialog = new DialogProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("people", people);
        bundle.putInt("branchId", branchId);
        bundle.putInt("nodeType", nodeType);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void showInformation(People people){
        tvProfile.setText("Information of " + people.getName());
        if(people.getGender() == 1){
            imgGenger.setImageResource(R.drawable.ic_male);
        } else {
            imgGenger.setImageResource(R.drawable.ic_female);
        }
        txtFullname.setText(people.getName());
        txtNickname.setText(people.getNickname());

        if (people.getBirthday() != null){
            txtBirthday.setText(people.getBirthday());
        }

        if (people.getDeathDay() != null){
            txtDeathday.setText(people.getDeathDay());
        }

        txtAddress.setText(people.getAddress());
        txtDescription.setText(people.getDescription());
    }

    @Override
    public void showRelationMap(List<People> peopleList) {
        mGetRelationInterface.sendDataToMap(peopleList);
        this.dismiss();
    }

    public interface GetRelationInterface{
        void sendDataToMap(List<People> peopleList);
    }

    public GetRelationInterface mGetRelationInterface;

    public void attackInterface(GetRelationInterface getRelationInterface){
        mGetRelationInterface = getRelationInterface;
    }

    @OnClick({R.id.btnAddNode, R.id.btnRelative,  R.id.btnEdit, R.id.btnMenu, R.id.btnClose})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.btnAddNode:
                DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance(people, null, nodeType);
                dialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
                dialogNodeFragment.attackInterface(new DialogNodeFragment.CreateNodeInterface() {
                    @Override
                    public void sendDataToMap(People people) {
                        mDialogProfileInterface.sendDataToMap(people);
                        getDialog().dismiss();
                    }
                });
                break;
            case R.id.btnRelative:
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                dialogProfileFragmentPresenterImpl = new DialogProfileFragmentPresenterImpl(this);
                dialogProfileFragmentPresenterImpl.getRelative(people.getId(), token);
                break;
            case R.id.btnEdit:
                UpdateDialogNodeFragment updateDialogNodeFragment = UpdateDialogNodeFragment.newInstance(people);
                updateDialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
                updateDialogNodeFragment.attackInterface(new UpdateDialogNodeFragment.UpdateNodeInterface() {
                    @Override
                    public void sendDataNodeProfile(People people) {
                        showInformation(people);
                        updatedPeople = people;
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(updatedPeople != null){
            mDialogProfileInterface.sendUpdateDataToMap(updatedPeople);
        }
    }

    public interface DialogProfileInterface{
        void sendDataToMap(People people);
        void sendUpdateDataToMap(People people);
    }

    public DialogProfileInterface mDialogProfileInterface;

    public void attackInterface(DialogProfileInterface dialogProfileInterface){
        mDialogProfileInterface = dialogProfileInterface;
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
