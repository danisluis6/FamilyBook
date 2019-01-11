package com.senior.project.genealogy.view.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.login.LoginActivity;
import com.senior.project.genealogy.view.activity.login.LoginPresenterImpl;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private LoginPresenterImpl loginPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseMessaging.getInstance().subscribeToTopic(Utils.getDeviceId());
        ButterKnife.bind(this);
        loginPresenterImpl = new LoginPresenterImpl(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        checkAccount();
    }

    private void checkAccount(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.USERNAME, Constants.EMPTY_STRING);
        String password = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.PASSWORD, Constants.EMPTY_STRING);
        if(!username.equals(Constants.EMPTY_STRING) && !password.equals(Constants.EMPTY_STRING))
        {
            User user = new User(username, password, Utils.getDeviceId());
            loginPresenterImpl.login(user, true);
        } else {
            showActivity(LoginActivity.class);
        }
    }

    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
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
}
