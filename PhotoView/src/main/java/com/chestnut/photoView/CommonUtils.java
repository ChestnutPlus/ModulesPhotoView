package com.chestnut.photoView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Chestnut on 2017/3/11.
 */

public class CommonUtils {

    private String TAG = "CommonUtils";

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * 隐藏状态栏
     * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
     * <p>此方法Activity可以继承AppCompatActivity</p>
     * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
     * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
     * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
     *
     * @param activity activity
     */
    public void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    private boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os!=null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void save(Bitmap bitmap, File file, SavePhotoCallBack savePhotoCallBack) {
        singleThreadExecutor.execute(new DownLoadImg(bitmap,file,savePhotoCallBack));
    }

    /**
     * 保存图片线程
     */
    private class DownLoadImg implements Runnable{

        private Bitmap bitmap = null;
        private File file = null;
        private SavePhotoCallBack savePhotoCallBack;

        public DownLoadImg(Bitmap bitmap, File file, SavePhotoCallBack savePhotoCallBack) {
            this.bitmap = bitmap;
            this.file = file;
            this.savePhotoCallBack = savePhotoCallBack;
        }

        @Override
        public void run() {
            try {
                boolean result = save(bitmap,file, Bitmap.CompressFormat.PNG,false);
                if (result) {
                    if (savePhotoCallBack != null)
                        savePhotoCallBack.saveSuccess(file);
                }
                else {
                    if (savePhotoCallBack != null)
                        savePhotoCallBack.saveFail(file);
                }
            } catch (Exception e) {
                Log.e(TAG,"DownLoadImg:"+e.getMessage());
                if (savePhotoCallBack != null)
                    savePhotoCallBack.saveFail(file);
            }
        }
    }

    /**
     * 保存回调
     */
    public interface SavePhotoCallBack {
        void saveSuccess(File file);
        void saveFail(File file);
    }

    public void release() {
        if (singleThreadExecutor!=null)
            singleThreadExecutor.shutdown();
    }
}
