package com.ex.rohit.Hola.loginAndRegister;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.ex.rohit.Hola.R;
import com.ex.rohit.Hola.http.HttpCallback;
import com.ex.rohit.Hola.http.HttpClient;
import com.ex.rohit.Hola.model.Success;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "RegisterActivity";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.bt_go)
    Button next;
    @BindView(R.id.et_password)
    EditText et_passwd;
    @BindView(R.id.et_repeatpassword)
    EditText et_repeatpassword;

    public String st_user;
    public String st_username;
    public String st_passwd;

    String st_repeatpassword;
    Map map = new HashMap();
    @BindView(R.id.et_username)
    EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ShowEnterAnimation();
    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_go:
                if (et_passwd.length() == 0) {
                    Snackbar.make(getWindow().getDecorView(), "密码不能为空", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_repeatpassword.length() == 0) {
                    Snackbar.make(getWindow().getDecorView(), "重复密码不能为空", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this, "重复密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                st_user =etUsername.getText().toString();
                st_passwd = et_passwd.getText().toString();
                st_repeatpassword = et_repeatpassword.getText().toString();
                if (!st_passwd.equals(st_repeatpassword)) {
                    Snackbar.make(getWindow().getDecorView(), "2次输入密码不同", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this, "2次输入密码不同", Toast.LENGTH_SHORT).show();
                    return;
                }
                TryToRegister(st_user, st_passwd);
                break;
            case R.id.fab:
                animateRevealClose();
                break;
        }
    }

    private void TryToRegister(String st_user, String st_passwd) {
        HttpClient.register(st_user, st_passwd, new HttpCallback<Success>() {
            @Override
            public void onSuccess(Success success) {
                if(success.getCode()==0){
                    animateRevealClose();

                    Log.d("register","success");
                }else {
                    Log.d("register","fail");
                    Snackbar.make(getWindow().getDecorView(), "注册失败(Server)", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Exception e) {
                Snackbar.make(getWindow().getDecorView(), "注册失败(Local)", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


}
