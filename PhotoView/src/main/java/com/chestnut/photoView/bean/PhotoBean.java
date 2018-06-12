package com.chestnut.photoView.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chestnut on 2017/3/8.
 */

public class PhotoBean implements Parcelable {

    public String title = null;
    public String url = null;

    public PhotoBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected PhotoBean(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<PhotoBean> CREATOR = new Creator<PhotoBean>() {
        @Override
        public PhotoBean createFromParcel(Parcel source) {
            return new PhotoBean(source);
        }

        @Override
        public PhotoBean[] newArray(int size) {
            return new PhotoBean[size];
        }
    };
}
