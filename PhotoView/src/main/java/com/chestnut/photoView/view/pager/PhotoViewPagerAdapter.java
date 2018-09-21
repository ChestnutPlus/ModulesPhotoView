package com.chestnut.photoView.view.pager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.chestnut.common.manager.imgloader.ImgLoaderConfig;
import com.chestnut.common.manager.imgloader.ImgLoaderManager;
import com.chestnut.common.manager.imgloader.contract.ImgLoaderListener;
import com.chestnut.photoView.R;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.view.core.PhotoView;
import com.chestnut.photoView.view.progress.CBProgressBar;

import java.util.List;


/**
 * Created by Chestnut on 2017/3/8.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {

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
            ImgLoaderManager.getInstance().load(context, ImgLoaderConfig.builder()
                    .from(mListPhotoBeans.get(i).url)
                    .err(R.drawable.chestnut_photo_view_load_fail)
                    .listen(new ImgLoaderListener() {
                        @Override
                        public void onReady(Drawable drawable) {
                            mListViews.get(index).findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onErr() {
                            mListViews.get(index).findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onProgress(int progress) {
                            CBProgressBar cbProgressBar = (CBProgressBar) mListViews.get(index).findViewById(R.id.loading);
                            cbProgressBar.setVisibility(View.VISIBLE);
                            cbProgressBar.setMax(100);
                            cbProgressBar.setProgress(progress);
                        }
                    })
                    .to(photoView)
                    .build());
        }
    }
}
