package com.chestnut.photoView.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chestnut.photoView.R;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.contract.PhotoViewContract;
import com.chestnut.photoView.presenter.PhotoViewPresenter;
import com.chestnut.photoView.view.pager.PhotoViewPagerAdapter;

import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener,PhotoViewContract.V {

    public static String Key_Photo_List = "Key_Photo_List";
    private PhotoViewPresenter photoViewPresenter = new PhotoViewPresenter(this,this);
    private TextView tvPagerIndex,tvTitle;
    private ViewPager viewPager;
    private ImageView imgDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //init view
        setContentView(R.layout.chestnut_photo_view_activity_photo_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvPagerIndex = (TextView) findViewById(R.id.tv_pager_index);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        imgDownload = (ImageView) findViewById(R.id.img_download);
        //init presenter
        photoViewPresenter.onCreate(getIntent());
        //set listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                photoViewPresenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.img_back).setOnClickListener(this);
        imgDownload.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoViewPresenter.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.img_back) {
            finish();
        }
        else if (i == R.id.img_download) {
            photoViewPresenter.saveCurrentPhoto();
        }
    }

    @Override
    public void setPagerIndex(int now, int total) {
        runOnUiThread(()->{
            if (tvPagerIndex!=null) {
                String str = "{x}/{y}";
                tvPagerIndex.setText(str.replace("{x}",String.valueOf(now)).replace("{y}",String.valueOf(total)));
            }
        });
    }

    @Override
    public void setTvTitle(String title) {
        runOnUiThread(()->{
            if (tvTitle!=null && title!=null)
                tvTitle.setText(title);
        });
    }

    @Override
    public void refreshViewPager(ArrayList<PhotoBean> photoBeanArrayList) {
        runOnUiThread(()->{
            LayoutInflater inflater = getLayoutInflater();
            ArrayList<View> views = new ArrayList<>();
            for (int i = 0; i < photoBeanArrayList.size(); i++) {
                views.add(inflater.inflate(R.layout.chestnut_photo_view_pager_photo_view, null));
            }
            PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(views, photoBeanArrayList);
            viewPager.removeAllViews();
            viewPager.setAdapter(photoViewPagerAdapter);
            photoViewPagerAdapter.loadImg(this);
        });
    }

    @Override
    public void showDownloadIcon(boolean isShow) {
        runOnUiThread(()-> imgDownload.setVisibility(isShow?View.VISIBLE:View.INVISIBLE));
    }
}
