package com.ex.rohit.sketchpad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Flash on 2018/4/19.
 */

public class Success {
    @SerializedName("error_code")
    private int code;

    public Success(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
