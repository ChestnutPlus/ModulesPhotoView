package com.chestnut.PhotoView;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;


/**
 * Created by Chestnut on 2017/3/8.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {

    private String TAG = "PhotoViewPagerAdapter";
    private List<View> mListViews;
    private List<PhotoBean> mListPhotoBeans;

    public PhotoViewPagerAdapter(List<View> mListViews, List<PhotoBean> mListPhotoBeans) {
        this.mListViews = mListViews;
        this.mListPhotoBeans = mListPhotoBeans;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position),0);
        return mListViews.get(position);
    }

    public void loadImg(final Context context) {
        for (int i = 0; i < mListPhotoBeans.size(); i++) {
            final int index = i;
            PhotoView photoView = (PhotoView) mListViews.get(index).findViewById(R.id.photoView);
            photoView.enable();
            Glide.with(context)
                    .load(mListPhotoBeans.get(i).url)
                    .asBitmap()
                    .thumbnail(0.1f)
                    .error(R.drawable.img_fail_load)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<Bitmap> target, boolean b) {
                            Log.e(TAG,"onException:"+e.getMessage()+",s:"+s);
                            mListViews.get(index).findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                            mListPhotoBeans.get(index).bitmap = null;
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap bitmap, String s, Target<Bitmap> target, boolean b, boolean b1) {
                            mListViews.get(index).findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                            mListPhotoBeans.get(index).bitmap = bitmap;
                            return false;
                        }
                    })
                    .into(photoView);
        }
    }
}
