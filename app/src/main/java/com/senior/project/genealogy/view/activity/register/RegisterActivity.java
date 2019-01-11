package com.senior.project.genealogy.view.activity.register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.login.LoginActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    @BindView(R.id.username)
    TextInputEditText edtUsername;

    @BindView(R.id.password)
    TextInputEditText edtPassword;

    @BindView(R.id.fullname)
    TextInputEditText edtFullname;

    @BindView(R.id.address)
    TextInputEditText edtAddress;

    @BindView(R.id.mail)
    TextInputEditText edtMail;

    @BindView(R.id.birthday)
    TextInputEditText edtBirthday;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.radioFemale)
    RadioButton radioFemale;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    private RegisterPresenterImpl registerPresenterImpl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if (edtMail != null)
            edtMail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE ||
                            keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                    keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        Utils.hiddenKeyBoard(RegisterActivity.this);
                        return true;
                    }
                    return false;
                }
            });
        registerPresenterImpl = new RegisterPresenterImpl(this);
    }

    @OnTextChanged({R.id.username, R.id.password, R.id.fullname})
    protected void onTextChanged() {

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()){
            btnRegister.setBackgroundResource(R.drawable.radius_button_disable);
            btnRegister.setEnabled(false);
        }
        else {
            btnRegister.setBackgroundResource(R.drawable.radius_button);
            btnRegister.setEnabled(true);
        }

    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnRegister, R.id.btnBack, R.id.birthday})
    public void onClick(View view)
    {
        if (Utils.isDoubleClick()) return;
        switch(view.getId())
        {
            case R.id.btnRegister:
                if (isValidData()) {
                    resetValidDataFields();
                    String password = BCrypt.hashpw(edtPassword.getText().toString(), BCrypt.gensalt());
                    User user = new User();
                    user.setUsername(edtUsername.getText().toString());
                    user.setPassword(password);
                    user.setFullname(edtFullname.getText().toString());
                    user.setMail(edtMail.getText().toString());
                    user.setAddress(edtAddress.getText().toString());
                    user.setBirthday(edtBirthday.getText().toString());

                    if(radioFemale.isChecked()){
                        user.setGender(false);
                    } else {
                        user.setGender(true);
                    }
                    registerPresenterImpl.register(user);
                }
                break;
            case R.id.btnBack:
                showActivity(LoginActivity.class);
                break;
            case R.id.birthday:
                selectDate();
                break;
        }
    }

    private boolean isValidData() {
        boolean errorOccurred = false;
        if (!Utils.isValidUsername(edtUsername.getText().toString())) {
            edtUsername.requestFocus();
            edtUsername.setError(getString(R.string.error_username));
            errorOccurred = true;
        } else if (!Utils.isValidPassword(edtPassword.getText().toString())) {
            edtPassword.requestFocus();
            edtPassword.setError(getString(R.string.error_password));
            errorOccurred = true;
        } else if (edtFullname.getText().toString().length() == 0) {
            edtFullname.requestFocus();
            edtFullname.setError(getString(R.string.error_fullname));
            errorOccurred = true;
        }
        return !errorOccurred;
    }

    private void resetValidDataFields() {
        edtUsername.setError(null);
        edtPassword.setError(null);
    }

    private Calendar mCalendar;

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    private void selectDate(){
        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthday.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog  progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void saveUser(String token, String avatar, String fullname) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_KEY.TOKEN,"Token " + token);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.AVATAR,avatar);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.FULLNAME,fullname);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        showActivity(LoginActivity.class);
    }

    @Override
    public void saveAccount(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_KEY.USERNAME,username);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.PASSWORD,password);
        editor.apply();
    }
}
