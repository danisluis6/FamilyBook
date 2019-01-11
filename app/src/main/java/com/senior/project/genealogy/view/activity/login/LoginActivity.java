package com.senior.project.genealogy.view.activity.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.OverrideFonts;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.username)
    EditText edtUsername;

    @BindView(R.id.password)
    EditText edtPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.lnSignup)
    LinearLayout lnSignup;

    private LoginPresenterImpl loginPresenterImpl;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseMessaging.getInstance().subscribeToTopic(Utils.getDeviceId());
        ButterKnife.bind(this);
        initFonts();
        loginPresenterImpl = new LoginPresenterImpl(this);
    }

    private void initFonts() {
        edtUsername.setTypeface(OverrideFonts.getTypeFace(this, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
        edtPassword.setTypeface(OverrideFonts.getTypeFace(this, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.LIGHT));
    }

    @OnTextChanged({R.id.username, R.id.password})
    protected void onTextChanged() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()){
            btnLogin.setBackgroundResource(R.drawable.radius_button_disable);
            btnLogin.setEnabled(false);
        }
        else {
            btnLogin.setBackgroundResource(R.drawable.radius_button);
            btnLogin.setEnabled(true);
        }
    }
    @OnClick({R.id.btnLogin, R.id.lnSignup})
    public void onClick(View view)
    {
        Utils.hiddenKeyBoard(this);
        switch(view.getId())
        {
            case R.id.btnLogin:
                User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString(), Utils.getDeviceId());
                loginPresenterImpl.login(user, false);
                saveAccount(user.getUsername(), user.getPassword());
                break;
            case R.id.lnSignup:
                showActivity(RegisterActivity.class);
                break;
        }
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
        if (!isFinishing() && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void showLoginAgainDialog() {
        if (!isFinishing() && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        showLoginAlertDialog();
    }

    public void showLoginAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your connection and try again or exit and retry later");
        builder.setCancelable(false);
        builder.setPositiveButton("retry", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString(), Utils.getDeviceId());
                loginPresenterImpl.login(user, false);
            }
        });
        builder.setNegativeButton("exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void saveUser(String token, String avatar, String fullname, String deviceId) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_KEY.TOKEN,"Token " + token);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.AVATAR,avatar);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.FULLNAME,fullname);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.DEVICE_ID, deviceId);
        editor.apply();
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
