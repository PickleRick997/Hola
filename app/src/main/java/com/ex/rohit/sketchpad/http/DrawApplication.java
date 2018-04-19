package com.ex.rohit.sketchpad.http;
import android.app.Application;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;


public class DrawApplication extends Application {

    public static String ip = "http://192.168.123.221/";//api_secret
    public static int isLogin = 0;

}
