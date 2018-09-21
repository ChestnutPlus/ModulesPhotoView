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
 *     time  : 2018/9/20 17:55
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class PhotoViewer {

    private PhotoViewer(Builder builder){
        Intent intent = new Intent(builder.context, PhotoViewActivity.class);
        if (!(builder.context instanceof Activity))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PhotoViewActivity.Key_Photo_List,builder.photoBeanArrayList);
        bundle.putParcelable("callback", builder.photoViewerCallback);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 定义属性
     */
    public static final class Builder {

        private ArrayList<PhotoBean> photoBeanArrayList;
        private PhotoViewerCallback photoViewerCallback;
        private Context context;

        public PhotoViewer build(Context context) {
            this.context = context;
            return new PhotoViewer(this);
        }

        public void setPhotoBeanArrayList(ArrayList<PhotoBean> photoBeanArrayList) {
            this.photoBeanArrayList = photoBeanArrayList;
        }

        public void setPhotoViewerCallback(PhotoViewerCallback photoViewerCallback) {
            this.photoViewerCallback = photoViewerCallback;
        }
    }
}
