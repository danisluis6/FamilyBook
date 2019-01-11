package com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blox.graphview.Node;

public class UpdateDialogNodeFragment extends DialogFragment implements UpdateDialogNodeFragmentView {

    @BindView(R.id.edtBirthday)
    EditText edtBirthday;

    @BindView(R.id.edtDeathday)
    EditText edtDeathday;

    @BindView(R.id.tilBirthday)
    TextInputLayout tilBirthday;

    @BindView(R.id.txtUpdateNode)
    TextView txtUpdateNode;

    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtNickname)
    EditText edtNickname;

    @BindView(R.id.edtDescription)
    EditText edtDescription;

    @BindView(R.id.tilDeathday)
    TextInputLayout tilDeathday;

    @BindView(R.id.flDeathday)
    FrameLayout flDeathday;

    @BindView(R.id.btnDeleteBirthday)
    ImageButton btnDeleteBirthday;

    @BindView(R.id.btnDeleteDeathday)
    ImageButton btnDeleteDeathday;

    @BindView(R.id.imgGenger)
    ImageView imgGenger;

    private UpdateDialogNodeFragmentPresenterImpl dialogNodeFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private People people;
    private Integer genderBeforeUpdate;

    public UpdateDialogNodeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_node_update, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);

        if(getArguments().getSerializable("people") != null){
            people = (People) getArguments().getSerializable("people");
            txtUpdateNode.setText("Update profile of " + ((People)getArguments().getSerializable("people")).getName());
            if(((People)getArguments().getSerializable("people")).getGender() == 1) {
                imgGenger.setImageResource(R.drawable.ic_male);
            } else {
                imgGenger.setImageResource(R.drawable.ic_female);
            }
        }
        dialogNodeFragmentPresenterImpl = new UpdateDialogNodeFragmentPresenterImpl(this);
        showInformation(people);
        return view;
    }

    public void showInformation(People people){
        if(people.getGender() == 1){
            imgGenger.setImageResource(R.drawable.ic_male);
        } else {
            imgGenger.setImageResource(R.drawable.ic_female);
        }

        genderBeforeUpdate = people.getGender();
        edtFullName.setText(people.getName());
        edtNickname.setText(people.getNickname());

        if (people.getBirthday() != null){
            edtBirthday.setText(people.getBirthday());
        }

        flDeathday.setVisibility(View.VISIBLE);
        if (people.getDeathDay() != null){
            edtDeathday.setText(people.getDeathDay());
        }

        edtAddress.setText(people.getAddress());
        edtDescription.setText(people.getDescription());
    }

    public static UpdateDialogNodeFragment newInstance(People people) {
        UpdateDialogNodeFragment dialog = new UpdateDialogNodeFragment();
        Bundle bundle = new Bundle();
        if(people != null){
            bundle.putSerializable("people", people);
        }
        dialog.setArguments(bundle);
        return dialog;
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnSave, R.id.btnClose, R.id.edtBirthday, R.id.edtDeathday, R.id.btnDeleteBirthday, R.id.btnDeleteDeathday})
    void onClick(View view) {
        if (Utils.isDoubleClick())  return;
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.edtBirthday:
                selectDate(edtBirthday);
                break;
            case R.id.edtDeathday:
                selectDate(edtDeathday);
                break;
            case R.id.btnDeleteBirthday:
                edtBirthday.setText("");
                edtDeathday.setText("");
                flDeathday.setVisibility(View.GONE);
                break;
            case R.id.btnDeleteDeathday:
                edtDeathday.setText("");
                break;
            case R.id.btnSave:
                if (!isValidData()) return;
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");

                people.setName(edtFullName.getText().toString());
                people.setNickname(edtNickname.getText().toString());
                people.setAddress(edtAddress.getText().toString());
                people.setDescription(edtDescription.getText().toString());

                if(!Constants.EMPTY_STRING.equals(edtBirthday.getText().toString())){
                    people.setBirthday(edtBirthday.getText().toString());
                }

                if(!Constants.EMPTY_STRING.equals(edtDeathday.getText().toString())){
                    people.setDeathDay(edtDeathday.getText().toString());
                }

                dialogNodeFragmentPresenterImpl.updatePeople(people ,token);
                break;
        }
    }

    public void showChangeGenderAlertDialog(final People people){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change gender");
        builder.setMessage("If you change gender to female. All children of him will be deleted. \nAre you sure you want to change it? ");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogNodeFragmentPresenterImpl.updatePeople(people ,token);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private Calendar mCalendar;
    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    private void selectDate(final EditText edt){
        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        if (edt == edtBirthday){
            mCalendar.set(1500,1,1);
            datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
            if(flDeathday.getVisibility() == View.GONE){
                flDeathday.setVisibility(View.VISIBLE);
            }
        }
        if (edt == edtDeathday){
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            Date birthday = new Date();
            try {
                birthday = new SimpleDateFormat("dd/MM/yyyy").parse(edtBirthday.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mCalendar.setTime(birthday);
            datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
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
    public void closeDialogFragment(People people) {
        mUpdateNodeInterface.sendDataNodeProfile(people);
        this.dismiss();
    }

    public boolean isValidData() {
        boolean errorOccurred = false;
        if (edtFullName.getText().toString().length() == 0) {
            edtFullName.requestFocus();
            edtFullName.setError(getString(R.string.error_fullname));
            errorOccurred = true;
        }
        return !errorOccurred;
    }

    public interface UpdateNodeInterface{
        void sendDataNodeProfile(People people);
    }

    public UpdateNodeInterface mUpdateNodeInterface;

    public void attackInterface(UpdateNodeInterface updateNodeInterface){
        mUpdateNodeInterface = updateNodeInterface;
    }


}
