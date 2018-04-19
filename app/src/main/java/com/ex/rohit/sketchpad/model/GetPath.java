package com.ex.rohit.sketchpad.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Flash on 2018/4/17.
 */

public class GetPath {
    @SerializedName("loacl_id")
    private String local_id;

    public GetPath(String local_id) {
        this.local_id = local_id;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }
}
