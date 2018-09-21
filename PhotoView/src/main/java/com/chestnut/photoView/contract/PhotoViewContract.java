package com.chestnut.photoView.contract;

import android.content.Intent;
import android.graphics.Typeface;

import com.chestnut.photoView.bean.PhotoBean;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/5/25 14:16
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public interface PhotoViewContract {
    interface P {
        void onCreate(Intent intent);
        void onPageSelected(int position);
        void saveCurrentPhoto();
        void onDestroy();
    }
    interface V {
        void setPagerIndex(int now, int total);
        void setTvTitle(String tvTitle);
        void refreshViewPager(ArrayList<PhotoBean> photoBeanArrayList);
        void showDownloadIcon(boolean isShow);
        void setTypeface(Typeface typeface);
    }
}
