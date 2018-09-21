package com.chestnut.photoView.view.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chestnut.photoView.R;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.view.core.PhotoView;
import com.chestnut.photoView.view.progress.CBProgressBar;

import java.util.LinkedList;
import java.util.List;

public class PhotoViewPagerAdapter extends PagerAdapter {

    private List<PhotoBean> mListPhotoBeans;
    private LinkedList<View> mCaches = new LinkedList<>();

    public PhotoViewPagerAdapter(List<PhotoBean> mListPhotoBeans) {
        this.mListPhotoBeans = mListPhotoBeans;
    }

    @Override
    public int getCount() {
        return mListPhotoBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(mCaches.size() > 0){
            mCaches.clear();
        }
        container.removeView((View)object);
        mCaches.add((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View convertView;
        ViewHolder mHolder;
        if(mCaches.size() == 0){
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.chestnut_photo_view_pager_photo_view,null);
            mHolder = new ViewHolder();
            mHolder.photoView = (PhotoView)convertView.findViewById(R.id.photoView);
            mHolder.cbProgressBar = (CBProgressBar)convertView.findViewById(R.id.loading);
            convertView.setTag(mHolder);
        }else{
            convertView = mCaches.removeFirst();
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.fresh(container.getContext(), mListPhotoBeans.get(position));
        container.addView(convertView);
        return convertView;
    }

    private class ViewHolder{
        PhotoView photoView;
        CBProgressBar cbProgressBar;
        void fresh(Context context, PhotoBean photoBean) {
            photoView.enable();
            Glide.with(context)
                    .load(photoBean.url)
                    .error(R.drawable.chestnut_photo_view_load_fail)
                    .thumbnail(0.2f)
                    .into(photoView);
        }
    }
}
