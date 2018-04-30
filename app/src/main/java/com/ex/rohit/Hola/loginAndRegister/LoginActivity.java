package com.ex.rohit.Hola.loginAndRegister;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.ex.rohit.Hola.MainActivity;
import com.ex.rohit.Hola.R;
import com.ex.rohit.Hola.http.HttpCallback;
import com.ex.rohit.Hola.http.HttpClient;
import com.ex.rohit.Hola.model.Success;

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



    private void TryToLogin(final String stUserName,final String stPassWord) {
        HttpClient.login(stUserName, stPassWord, new HttpCallback<Success>() {
            @Override
            public void onSuccess(Success success) {
                if(success.getCode()==0){
                    if(sp.getBoolean("REM", false)) {
                        sp.edit().putString("user_name", stUserName).commit();
                        sp.edit().putString("password", stPassWord).commit();
                    }
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
        //是否记住密码
        if (sp.getBoolean("REM", false)) {
            username.setText(sp.getString("user_name", ""));
            userpassword.setText(sp.getString("password", ""));
            remember.setChecked(true);
        } else {
            username.setText(sp.getString("user_name", ""));
            userpassword.setText("");
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
