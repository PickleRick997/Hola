package com.ex.rohit.Hola.http;

import com.ex.rohit.Hola.model.Success;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import com.ex.rohit.Hola.model.CustomPath;
import com.ex.rohit.Hola.model.PathGroup;

import okhttp3.Call;
import okhttp3.MediaType;


/**
 * 网络操作类
 */
public class HttpClient {

    public  static Gson gson = new Gson();
    public static void getPath(String last_id, final HttpCallback<PathGroup> callback) {
        OkHttpUtils
                .get().url(DrawApplication.ip+"hola/getStroke.php"+"?last_id="+last_id)
                .build()
                .execute(new JsonCallback<PathGroup>(PathGroup.class) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onResponse(PathGroup response, int id) {
                        callback.onSuccess(response);
                    }
                });
    }

    public static void sendPath(String user_id, CustomPath path, String color,float brushSize, final HttpCallback<String> callback) throws IOException {
        //String str = gson.toJson(path.getActions());
        String pathstr = ParcelableUtil.writeToStr(path);
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("stroke",pathstr);
        json.put("user_id","1");
        json.put("color",color);
        json.put("brush_size",brushSize);
        OkHttpUtils
                .postString()
                .url(DrawApplication.ip+"hola/addStroke.php")
                .content(json.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callback.onSuccess(response);
                    }
                });
    }

    public static void login(String username, String passwd,final HttpCallback<Success> callback) {
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("user_name",username);
        json.put("passwd",passwd);
        OkHttpUtils
                .postString()
                .url(DrawApplication.ip+"hola/login.php")
                .content(json.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new JsonCallback<Success>(Success.class) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onResponse(Success response, int id) {
                        callback.onSuccess(response);
                    }
                });
    }

    public static void register(String username, String passwd,final HttpCallback<Success> callback) {
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("user_name",username);
        json.put("passwd",passwd);
        OkHttpUtils
                .postString()
                .url(DrawApplication.ip+"hola/register.php")
                .content(json.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new JsonCallback<Success>(Success.class) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onFail(e);
                    }

                    @Override
                    public void onResponse(Success response, int id) {
                        callback.onSuccess(response);
                    }
                });
    }
}
