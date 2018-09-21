package com.chestnut.photoView.contract;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/9/20 18:26
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class PhotoViewerCallback implements Parcelable{
    public void onStart(String saveSuccessFilePath){}
    public void onSuccess(String saveSuccessFilePath){}
    public void onFail(String saveSuccessFilePath){}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PhotoViewerCallback() {
    }

    protected PhotoViewerCallback(Parcel in) {
    }

    public static final Creator<PhotoViewerCallback> CREATOR = new Creator<PhotoViewerCallback>() {
        @Override
        public PhotoViewerCallback createFromParcel(Parcel source) {
            return new PhotoViewerCallback(source);
        }

        @Override
        public PhotoViewerCallback[] newArray(int size) {
            return new PhotoViewerCallback[size];
        }
    };
}
