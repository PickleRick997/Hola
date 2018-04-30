package com.ex.rohit.Hola.model;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import com.ex.rohit.Hola.http.ParcelableUtil;

/**
 * Created by Flash on 2018/4/17.
 */

public class PathJson {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("stroke")
    private String custompath;
    @SerializedName("color")
    private String color;
    @SerializedName("brushSize")
    private float brushSize;

    public PathJson(String id, String user_id, String custompath, String color, float brushSize) {
        this.id = id;
        this.user_id = user_id;
        this.custompath = custompath;
        this.color = color;
        this.brushSize = brushSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CustomPath getCustompath() {
        CustomPath customPath = new CustomPath();
        try {
            customPath = (CustomPath)ParcelableUtil.deserializeFromStr(this.custompath);

        } catch (IOException e) {
            e.printStackTrace();
        };
        return customPath;
    }

    public void setCustompath(String custompath) {
        this.custompath = custompath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(float brushSize) {
        this.brushSize = brushSize;
    }
}
