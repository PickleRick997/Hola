package com.ex.rohit.sketchpad.model;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Flash on 2018/4/17.
 */

public class MyPath implements Parcelable {
    private Path path;

    public MyPath(Path path) {
        this.path = path;
    }

    protected MyPath(Parcel in) {
    }

    public static final Creator<MyPath> CREATOR = new Creator<MyPath>() {
        @Override
        public MyPath createFromParcel(Parcel in) {
            return new MyPath(in);
        }

        @Override
        public MyPath[] newArray(int size) {
            return new MyPath[size];
        }
    };

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
