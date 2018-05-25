package com.chestnut.photoView.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.view.activity.PhotoViewActivity;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/5/25 15:28
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class PhotoConfig {

    /*单例*/
    private static volatile PhotoConfig defaultInstance;
    public static PhotoConfig getInstance() {
        PhotoConfig xFontUtils = defaultInstance;
        if (defaultInstance == null) {
            synchronized (PhotoConfig.class) {
                xFontUtils = defaultInstance;
                if (defaultInstance == null) {
                    xFontUtils = new PhotoConfig();
                    defaultInstance = xFontUtils;
                }
            }
        }
        return xFontUtils;
    }
    private PhotoConfig(){}

    /*变量*/
    private SavePhotoCallback savePhotoCallback;
    private boolean isOpenDownload = true;

    public SavePhotoCallback getSavePhotoCallback() {
        return savePhotoCallback;
    }

    public PhotoConfig setSavePhotoCallback(SavePhotoCallback savePhotoCallback) {
        this.savePhotoCallback = savePhotoCallback;
        return this;
    }

    public boolean isOpenDownload() {
        return isOpenDownload;
    }

    public PhotoConfig setOpenDownload(boolean openDownload) {
        isOpenDownload = openDownload;
        return this;
    }

    public void toViewPhoto(Context context, PhotoBean photoBean) {
        ArrayList<PhotoBean> arrayList = new ArrayList<>();
        arrayList.add(photoBean);
        toViewPhoto(context, arrayList);
    }

    public void toViewPhoto(Context context, ArrayList<PhotoBean> photoBeanArrayList) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        if (!(context instanceof Activity))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PhotoViewActivity.Key_Photo_List,photoBeanArrayList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /*接口*/
    public interface SavePhotoCallback {
        void onStart(String saveSuccessFilePath);
        void onSuccess(String saveSuccessFilePath);
        void onFail(String saveSuccessFilePath);
    }
}
