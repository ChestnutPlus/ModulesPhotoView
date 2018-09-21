package com.chestnut.photoView.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.LongSparseArray;

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

    public static String Key_Builder = "Key_Builder";

    private LongSparseArray<Builder> builderLongSparseArray = new LongSparseArray<>();

    /*单例*/
    private static volatile PhotoViewer defaultInstance;
    public static PhotoViewer getInstance() {
        PhotoViewer xFontUtils = defaultInstance;
        if (defaultInstance == null) {
            synchronized (PhotoViewer.class) {
                xFontUtils = defaultInstance;
                if (defaultInstance == null) {
                    xFontUtils = new PhotoViewer();
                    defaultInstance = xFontUtils;
                }
            }
        }
        return xFontUtils;
    }
    private PhotoViewer(){}

    /**
     * 执行跳转
     * @param builder builder
     * @return this
     */
    private PhotoViewer execute(Builder builder){
        long sparseKey = System.currentTimeMillis();
        builderLongSparseArray.put(sparseKey,builder);
        Intent intent = new Intent(builder.context, PhotoViewActivity.class);
        if (!(builder.context instanceof Activity))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Key_Builder,sparseKey);
        builder.context.startActivity(intent);
        return this;
    }

    /**
     * 得到建造者
     * @return 建造者
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * pop出 builder
     * @param key key
     * @return builder
     */
    public synchronized Builder pop(long key) {
        return builderLongSparseArray.get(key,null);
    }

    public synchronized Builder popAndClean(long key) {
        Builder builder = builderLongSparseArray.get(key,null);
        builderLongSparseArray.clear();
        return builder;
    }

    /**
     * PhotoViewer
     *  建造者
     */
    public static final class Builder {

        public ArrayList<PhotoBean> photoBeanArrayList = new ArrayList<>();
        public Callback photoViewerCallback;
        public boolean enableDownload = false;
        public Typeface typeface = null;
        public boolean defaultShowPermissionToastTips = false;
        private Context context;

        public PhotoViewer build(Context context) {
            this.context = context;
            return PhotoViewer.getInstance().execute(this);
        }

        public Builder setPhotoBeanArrayList(ArrayList<? extends PhotoBean> photoBeanArrayList) {
            this.photoBeanArrayList.addAll(photoBeanArrayList);
            return this;
        }

        public <T extends PhotoBean> Builder setPhotoBean(T t) {
            this.photoBeanArrayList.add(t);
            return this;
        }

        public Builder setPhotoViewerCallback(Callback photoViewerCallback) {
            this.photoViewerCallback = photoViewerCallback;
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        public Builder setEnableDownload(boolean enableDownload) {
            this.enableDownload = enableDownload;
            return this;
        }

        public Builder setDefaultShowPermissionToastTips(boolean defaultShowPermissionToastTips) {
            this.defaultShowPermissionToastTips = defaultShowPermissionToastTips;
            return this;
        }
    }

    /**
     * 回调
     */
    public static abstract class Callback {
        public void onSaveStart(String saveSuccessFilePath){}
        public void onSaveSuccess(String saveSuccessFilePath){}
        public void onSaveFail(String saveSuccessFilePath){}
        public void onPermissionDenied(String permission){}
    }
}
