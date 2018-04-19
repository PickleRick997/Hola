package com.ex.rohit.sketchpad.loginAndRegister;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.ex.rohit.sketchpad.MainActivity;
import com.ex.rohit.sketchpad.R;
import com.ex.rohit.sketchpad.http.HttpCallback;
import com.ex.rohit.sketchpad.http.HttpClient;
import com.ex.rohit.sketchpad.model.Success;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText userpassword;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.remember_pass)
    CheckBox remember;
    @BindView(R.id.login_auto)
    CheckBox login_auto;


    private SharedPreferences sp;
    ProgressDialog proDialog;
    String stUserName;
    String stPassWord;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("userinfo", MODE_ENABLE_WRITE_AHEAD_LOGGING);//获得实例对象
        //CreateHandler();
        initButton();

    }



    private void TryToLogin(String stUserName, String stPassWord) {
        HttpClient.login(stUserName, stPassWord, new HttpCallback<Success>() {
            @Override
            public void onSuccess(Success success) {
                if(success.getCode()==0){
                    Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i2);
                    finish();
                    Log.d("register","success");
                }else {
                    Log.d("register","fail");
                    Snackbar.make(getWindow().getDecorView(), "登录失败(Server)", Snackbar.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFail(Exception e) {
                Snackbar.make(getWindow().getDecorView(), "登录失败(Local)", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initButton() {
        //自动登陆
        if (sp.getBoolean("AUTO", false)) {
            remember.setChecked(true);
            login_auto.setChecked(true);
            TryToLogin(sp.getString("user_name", ""), sp.getString("password", ""));
        }
        //是否记住密码
        if (sp.getBoolean("REM", false)) {
            username.setText(sp.getString("user_name", ""));
            String a = sp.getString("user_name", "");
            userpassword.setText(sp.getString("user_password", ""));
            remember.setChecked(true);
        } else {
            username.setText(sp.getString("user_name", ""));
            userpassword.setText("");
            login_auto.setChecked(false);
            remember.setChecked(false);
        }



        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    sp.edit().putBoolean("REM", true).commit();
                } else {
                    sp.edit().putBoolean("REM", false).commit();
                    sp.edit().putBoolean("AUTO", false).commit();
                    login_auto.setChecked(false);
                }
            }
        });
        login_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sp.edit().putBoolean("AUTO", true).commit();
                    sp.edit().putBoolean("REM", true).commit();
                } else {
                    sp.edit().putBoolean("AUTO", false).commit();
                    sp.edit().putBoolean("REM", false).commit();
                    remember.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(null);
                }
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                break;
            case R.id.bt_go:
                stUserName = username.getText().toString();
                stPassWord = userpassword.getText().toString();
                TryToLogin(stUserName, stPassWord);
                break;
        }
    }

    private void createProgressBar() {
        proDialog = ProgressDialog.show(LoginActivity.this, "请等待", "数据传送中！");
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
                proDialog.dismiss();//不写会程序会卡死。
            }
        };
        thread.start();
    }


}
