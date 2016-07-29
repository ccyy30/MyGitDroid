package com.feicui.mygitdroid.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicui.mygitdroid.MainActivity;
import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        //github授权登陆
        activityUtils.startActivity(LoginActivity.class);
        finish();
    }

    @OnClick(R.id.btnEnter)
    public void enter(){
        //直接进入
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

}
