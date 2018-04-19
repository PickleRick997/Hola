package com.ex.rohit.sketchpad.http;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import com.zhy.http.okhttp.callback.Callback;
/**
 * Created by Flash on 2018/4/17.
 */

public class PathCallback<T> extends Callback<T> {
    private Class<T> mClass;
    private Gson mGson;

    public PathCallback(Class<T> clazz) {
        this.mClass = clazz;
        mGson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        try {
            String jsonString = response.body().string();
            return mGson.fromJson(jsonString, mClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(T response, int id) {

    }
}
