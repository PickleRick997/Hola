package com.ex.rohit.sketchpad.model;

import android.graphics.Path;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Flash on 2018/4/17.
 */

public class Login {
    @SerializedName("username")
    private String username;
    @SerializedName("passwd")
    private String passwd;

    public Login(String username, String passwd) {
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
