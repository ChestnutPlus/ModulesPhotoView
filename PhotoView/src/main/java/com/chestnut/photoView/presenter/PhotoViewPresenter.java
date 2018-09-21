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
import com.chestnut.photoView.contract.PhotoConfig;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.chestnut.photoView.view.activity.PhotoViewActivity.Key_Photo_List;

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
    private ArrayList<PhotoBean> photoBeanArrayList;
    private int currentPosition = -1;
    private String TAG = "PhotoViewPresenter";
    private Context context;

    public PhotoViewPresenter(Context context, PhotoViewContract.V v) {
        this.context = context;
        this.v = v;
    }

    public void onCreate(Intent intent) {
        if (intent!=null && intent.getExtras()!=null) {
            photoBeanArrayList = intent.getExtras().getParcelableArrayList(Key_Photo_List);
            if (photoBeanArrayList != null && photoBeanArrayList.size()>0) {
                if (v!=null) {
                    v.refreshViewPager(photoBeanArrayList);
                    v.setTvTitle(photoBeanArrayList.get(0).title);
                    currentPosition = 1;
                    v.setPagerIndex(currentPosition, photoBeanArrayList.size());
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
        if (v!=null)
            v.showDownloadIcon(PhotoConfig.getInstance().isOpenDownload());
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (v!=null && photoBeanArrayList!=null) {
            v.setPagerIndex(currentPosition + 1, photoBeanArrayList.size());
            v.setTvTitle(photoBeanArrayList.get(currentPosition).title);
        }
    }

    @Override
    public void saveCurrentPhoto() {
        if (photoBeanArrayList!=null && currentPosition>0 && currentPosition<photoBeanArrayList.size()) {
            final PhotoBean p = photoBeanArrayList.get(currentPosition);
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
                        if (PhotoConfig.getInstance().getSavePhotoCallback()!=null)
                            PhotoConfig.getInstance().getSavePhotoCallback().onStart(s);
                        return SimpleDownloadUtils.downLoadRx(p.url, s);
                    })
                    .subscribe(aBoolean -> {}, throwable -> {
                        LogUtils.i(TAG, "err, "+throwable.getMessage());
                        if (PhotoConfig.getInstance().getSavePhotoCallback()!=null)
                            PhotoConfig.getInstance().getSavePhotoCallback().onFail(saveFilePath[0]);
                    }, ()->{
                        if (PhotoConfig.getInstance().getSavePhotoCallback()!=null)
                            PhotoConfig.getInstance().getSavePhotoCallback().onSuccess(saveFilePath[0]);
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
    public void onDestroy() {
        v = null;
    }
}
