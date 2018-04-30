package com.ex.rohit.Hola.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Flash on 2018/4/17.
 */

public class PathGroup {
    @SerializedName("data")
    private ArrayList<PathJson> pathGroup;

    public PathGroup(ArrayList<PathJson> pathGroup) {
        this.pathGroup = pathGroup;
    }

    public ArrayList<PathJson> getPathGroup() {
        return pathGroup;
    }

    public void setPathGroup(ArrayList<PathJson> pathGroup) {
        this.pathGroup = pathGroup;
    }
}
