package com.chestnut.photoView.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.chestnut.common.utils.EncryptUtils;
import com.chestnut.common.utils.LogUtils;
import com.chestnut.common.utils.SimpleDownloadUtils;
import com.chestnut.common.utils.StringUtils;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.contract.PhotoViewContract;
import com.chestnut.photoView.contract.PhotoViewer;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/5/25 14:18
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class PhotoViewPresenter implements PhotoViewContract.P{

    private PhotoViewContract.V v;
    private PhotoViewer.Builder builder;
    private int currentPosition = -1;
    private String TAG = "PhotoViewPresenter";
    private Context context;

    public PhotoViewPresenter(Context context, PhotoViewContract.V v) {
        this.context = context.getApplicationContext();
        this.v = v;
    }

    public void onCreate(Intent intent) {
        if (intent!=null && intent.getExtras()!=null) {
            long longKey = intent.getLongExtra(PhotoViewer.Key_Builder,-1);
            builder = PhotoViewer.getInstance().popAndClean(longKey);
            //参数注入
            if (builder!=null) {
                if (v!=null) {
                    v.showDownloadIcon(builder.enableDownload);
                    v.setTypeface(builder.typeface);
                }
            }
            if (builder!=null && builder.photoBeanArrayList != null && builder.photoBeanArrayList.size() > 0) {
                if (v != null) {
                    v.refreshViewPager(builder.photoBeanArrayList);
                    v.setTvTitle(builder.photoBeanArrayList.get(0).title);
                    currentPosition = 1;
                    v.setPagerIndex(currentPosition, builder.photoBeanArrayList.size());
                }
            }
            else {
                if (v!=null)
                    v.setPagerIndex(0,0);
            }
        }
        else {
            if (v!=null)
                v.setPagerIndex(0,0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (v!=null && builder!=null && builder.photoBeanArrayList!=null) {
            v.setPagerIndex(currentPosition + 1, builder.photoBeanArrayList.size());
            v.setTvTitle(builder.photoBeanArrayList.get(currentPosition).title);
        }
    }

    @Override
    public void saveCurrentPhoto() {
        if (builder!=null && builder.photoBeanArrayList!=null && currentPosition>0 && currentPosition<builder.photoBeanArrayList.size()) {
            final PhotoBean p = builder.photoBeanArrayList.get(currentPosition);
            String[] saveFilePath = new String[1];
            Observable.just(p)
                    //取文件名
                    .observeOn(Schedulers.io())
                    .map(photoBean -> {
                        if (StringUtils.isEmpty(photoBean.title)) {
                            String md5 = EncryptUtils.encryptMD5ToString(photoBean.url);
                            saveFilePath[0] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + md5 + ".png";
                            for (int i = 1;; i++) {
                                if (new File(saveFilePath[0]).exists()) {
                                    saveFilePath[0] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + md5 + "(" + i + ").png";
                                }
                                else
                                    break;
                            }
                        }
                        else {
                            saveFilePath[0] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + photoBean.title + ".png";
                            for (int i = 1;; i++) {
                                if (new File(saveFilePath[0]).exists()) {
                                    saveFilePath[0] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + photoBean.title + "(" + i + ").png";
                                }
                                else
                                    break;
                            }
                        }
                        LogUtils.i(TAG, saveFilePath[0]);
                        return saveFilePath[0];
                    })
                    //保存
                    .flatMap(s -> {
                        if (builder!=null && builder.photoViewerCallback!=null)
                            builder.photoViewerCallback.onSaveStart(s);
                        return SimpleDownloadUtils.downLoadRx(p.url, s);
                    })
                    .subscribe(aBoolean -> {}, throwable -> {
                        LogUtils.i(TAG, "err, "+throwable.getMessage());
                        if (builder!=null && builder.photoViewerCallback!=null)
                            builder.photoViewerCallback.onSaveFail(saveFilePath[0]);
                    }, ()->{
                        if (builder!=null && builder.photoViewerCallback!=null)
                            builder.photoViewerCallback.onSaveSuccess(saveFilePath[0]);
                        //通知系统插入图片
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(saveFilePath[0]);
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        context.sendBroadcast(mediaScanIntent);
                    });
        }
    }

    @Override
    public void onPermissionDenied(String permission) {
        if (builder!=null && builder.photoViewerCallback!=null)
            builder.photoViewerCallback.onPermissionDenied(permission);
    }

    @Override
    public void onDestroy() {
        v = null;
    }

    @Override
    public boolean isDefaultShowPermissionToastTips() {
        return builder!=null && builder.defaultShowPermissionToastTips;
    }
}
