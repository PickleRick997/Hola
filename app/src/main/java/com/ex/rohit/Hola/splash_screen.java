package com.ex.rohit.Hola;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ex.rohit.Hola.loginAndRegister.LoginActivity;

/**
 * Created by rohit on 7/2/17.
 */
public class splash_screen extends Activity
{
    private static int delay=1500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splshscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i= new Intent(splash_screen.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },delay);
    }
}

