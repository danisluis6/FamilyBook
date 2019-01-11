package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogNodeFragment extends DialogFragment implements DialogNodeFragmentView{


    @BindView(R.id.edtBirthday)
    EditText edtBirthday;

    @BindView(R.id.edtDeathday)
    EditText edtDeathday;

    @BindView(R.id.tilBirthday)
    TextInputLayout tilBirthday;

    @BindView(R.id.relativeType)
    Spinner spRelative;

    @BindView(R.id.txtRelative)
    TextView txtRelative;

    @BindView(R.id.tvNewNode)
    TextView txtNewNode;

    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtNickname)
    EditText edtNickname;

    @BindView(R.id.edtDescription)
    EditText edtDescription;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.tilDeathday)
    TextInputLayout tilDeathday;

    @BindView(R.id.flDeathday)
    FrameLayout flDeathday;

    @BindView(R.id.btnDeleteBirthday)
    ImageButton btnDeleteBirthday;

    @BindView(R.id.btnDeleteDeathday)
    ImageButton btnDeleteDeathday;

    private DialogNodeFragmentPresenterImpl dialogNodeFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private People people;

    public DialogNodeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_node, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        if (getArguments().getSerializable("people") != null){
            txtNewNode.setText("Add relative to " + ((People)getArguments().getSerializable("people")).getName());
        } else {
            txtNewNode.setText("Add first node");
            spRelative.setVisibility(View.GONE);
            txtRelative.setVisibility(View.GONE);
            radioGender.setVisibility(View.VISIBLE);
        }

        if(getArguments().getSerializable("people") != null){
            int nodeType = getArguments().getInt("nodeType");
            showSpinnerRelative(nodeType);
        } else {
            spRelative.setVisibility(View.GONE);
            showSpinnerRelative(Constants.NODE_TYPE.PARTNER);
        }
        return view;
    }

    public static DialogNodeFragment newInstance(People people, String branchId, int noteType) {
        DialogNodeFragment dialog = new DialogNodeFragment();
        Bundle bundle = new Bundle();
        if(people != null){
            bundle.putSerializable("people", people);
            bundle.putInt("nodeType", noteType);
        } else {
            bundle.putInt("branchId", Integer.valueOf(branchId));
        }
        dialog.setArguments(bundle);
        return dialog;
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnNewNode, R.id.btnClose, R.id.edtBirthday, R.id.edtDeathday, R.id.btnDeleteBirthday, R.id.btnDeleteDeathday})
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
            case R.id.btnNewNode:
                if (!isValidData()) return;
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                People newPeople = new People();

                newPeople.setName(edtFullName.getText().toString());
                newPeople.setNickname(edtNickname.getText().toString());
                newPeople.setAddress(edtAddress.getText().toString());
                newPeople.setDescription(edtDescription.getText().toString());

                if (getArguments().getSerializable("people") != null){
                    spRelative.setVisibility(View.GONE);
                    people = (People) getArguments().getSerializable("people");
                    newPeople.setBranchId(people.getBranchId());
                    String relativeType = spRelative.getSelectedItem().toString();
                    switch(relativeType)
                    {
                        case Constants.RELATIVE_TYPE.SON:
                            newPeople.setLifeIndex(people.getLifeIndex()+1);
                            newPeople.setParentId(people.getId());
                            newPeople.setGender(1);
                            break;
                        case Constants.RELATIVE_TYPE.DAUGHTER:
                            newPeople.setLifeIndex(people.getLifeIndex()+1);
                            newPeople.setParentId(people.getId());
                            newPeople.setGender(0);
                            break;
                        case Constants.RELATIVE_TYPE.BROTHER:
                            newPeople.setLifeIndex(people.getLifeIndex());
                            newPeople.setParentId(people.getParentId());
                            newPeople.setGender(1);
                            break;
                        case Constants.RELATIVE_TYPE.SISTER:
                            newPeople.setLifeIndex(people.getLifeIndex());
                            newPeople.setParentId(people.getParentId());
                            newPeople.setGender(0);
                            break;
                        case Constants.RELATIVE_TYPE.HUSBAND:
                            newPeople.setLifeIndex(people.getLifeIndex());
                            newPeople.setParentId(null);
                            newPeople.setGender(1);
                            newPeople.setPartnerId(people.getId());
                            break;
                        case Constants.RELATIVE_TYPE.WIFE:
                            newPeople.setLifeIndex(people.getLifeIndex());
                            newPeople.setParentId(null);
                            newPeople.setGender(0);
                            newPeople.setPartnerId(people.getId());
                            break;
                    }

                } else {
                    newPeople.setBranchId(getArguments().getInt("branchId"));
                    newPeople.setLifeIndex(0);
                    newPeople.setParentId(null);

                    if(radioMale.isChecked()) {
                        newPeople.setGender(1);
                    } else {
                        newPeople.setGender(0);
                    }
                }

                if(!Constants.EMPTY_STRING.equals(edtBirthday.getText().toString())){
                    newPeople.setBirthday(edtBirthday.getText().toString());
                }

                if(!Constants.EMPTY_STRING.equals(edtDeathday.getText().toString())){
                    newPeople.setDeathDay(edtDeathday.getText().toString());
                }

                dialogNodeFragmentPresenterImpl = new DialogNodeFragmentPresenterImpl(this);
                dialogNodeFragmentPresenterImpl.createPeople(newPeople ,token);
                break;
        }
    }

    public void showSpinnerRelative(int check) {
        ArrayAdapter<CharSequence> dataAdapter = null;
        switch (check){
            case Constants.NODE_TYPE.FIRST_MAN_MARRIED:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_first_male_node_married, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.FIRST_MAN_SINGLE:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_first_male_node_single, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.MAN_MARRIED:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_male_node_married, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.MAN_SINGLE:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_male_node_single, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.WOMAN_MARRIED:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_female_node_married, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.WOMAN_SINGLE:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_female_node_single, android.R.layout.simple_spinner_item);
                break;
            case Constants.NODE_TYPE.PARTNER:
                dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array_partner_node, android.R.layout.simple_spinner_item);
                break;
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRelative.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spRelative.setAdapter(dataAdapter);
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
    public void closeDialogFragment(List<People> peopleList) {
        mCreateNodeInterface.sendDataToMap(peopleList.get(0));
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

    public interface CreateNodeInterface{
        void sendDataToMap(People people);
    }

    public CreateNodeInterface mCreateNodeInterface;

    public void attackInterface(CreateNodeInterface createNodeInterface){
        mCreateNodeInterface = createNodeInterface;
    }

}
