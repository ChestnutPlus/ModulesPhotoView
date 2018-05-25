package com.chestnut.photoView.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chestnut on 2017/3/8.
 */

public class PhotoBean implements Parcelable {

    public String title = null;         //标题
    public String url = null;           //真正的地址
    public String thumbPic = null;      //缩略图地址
    public boolean isLocal = false;     //是否是本地图片
    public String desc = null;          //图片的描述

    public Bitmap bitmap = null;    //Glide加载完成后，对应的Drawable
    public boolean isSaveing = false;
    public boolean isSaveed = false;

    public PhotoBean(String title, String url, String thumbPic, boolean isLocal, String desc) {
        this.title = title;
        this.url = url;
        this.thumbPic = thumbPic;
        this.isLocal = isLocal;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "PhotoBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", thumbPic='" + thumbPic + '\'' +
                ", isLocal=" + isLocal +
                ", desc='" + desc + '\'' +
                '}';
    }

    /*  序列化
    * */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.thumbPic);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
        dest.writeString(this.desc);
    }

    protected PhotoBean(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.thumbPic = in.readString();
        this.isLocal = in.readByte() != 0;
        this.desc = in.readString();
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
