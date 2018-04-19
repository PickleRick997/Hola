package com.ex.rohit.sketchpad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Flash on 2018/4/17.
 */

public class Register {
    @SerializedName("username")
    private String username;
    @SerializedName("passwd")
    private String passwd;

    public Register(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
