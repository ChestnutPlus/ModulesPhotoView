package com.chestnut.photoView.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chestnut.photoView.R;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.contract.PhotoViewContract;
import com.chestnut.photoView.presenter.PhotoViewPresenter;
import com.chestnut.photoView.view.pager.PhotoViewPagerAdapter;

import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener,PhotoViewContract.V,ViewPager.OnPageChangeListener {

    private PhotoViewPresenter photoViewPresenter;
    private TextView tvPagerIndex,tvTitle;
    private ViewPager viewPager;
    private ImageView imgDownload;

    private final int requestWritePermissionCode = 0x111;
    private final int requestReadPermissionCode = 0x112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init view
        setContentView(R.layout.chestnut_photo_view_activity_photo_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvPagerIndex = (TextView) findViewById(R.id.tv_pager_index);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        imgDownload = (ImageView) findViewById(R.id.img_download);
        //set listener
        findViewById(R.id.img_back).setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        imgDownload.setOnClickListener(this);
        photoViewPresenter = new PhotoViewPresenter(this,this);
        //6.0权限检测，需要用到读取权限
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                case PackageManager.PERMISSION_GRANTED:
                    //init presenter
                    photoViewPresenter.onCreate(getIntent());
                    break;
                default:
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, requestReadPermissionCode);
                    break;
            }
        }
        else {
            //init presenter
            photoViewPresenter.onCreate(getIntent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(this);
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
            //6.0权限检测，需要用到存储权限
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switch (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    case PackageManager.PERMISSION_GRANTED:
                        photoViewPresenter.saveCurrentPhoto();
                        break;
                    default:
                        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestWritePermissionCode);
                        break;
                }
            }
            else {
                photoViewPresenter.saveCurrentPhoto();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case requestWritePermissionCode:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权成功
                    photoViewPresenter.saveCurrentPhoto();
                } else {
                    //授权失败提示
                    if (photoViewPresenter.isDefaultShowPermissionToastTips())
                        Toast.makeText(this,"no permission 0x01",Toast.LENGTH_SHORT).show();
                    photoViewPresenter.onPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case requestReadPermissionCode:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权成功
                    //init presenter
                    photoViewPresenter.onCreate(getIntent());
                }
                else {
                    //授权失败提示
                    if (photoViewPresenter.isDefaultShowPermissionToastTips())
                        Toast.makeText(this,"no permission 0x02",Toast.LENGTH_SHORT).show();
                    photoViewPresenter.onPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
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
            PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(photoBeanArrayList);
            viewPager.removeAllViews();
            viewPager.setAdapter(photoViewPagerAdapter);
        });
    }

    @Override
    public void showDownloadIcon(boolean isShow) {
        runOnUiThread(()-> imgDownload.setVisibility(isShow?View.VISIBLE:View.INVISIBLE));
    }

    @Override
    public void setTypeface(Typeface typeface) {
        if (typeface!=null && tvPagerIndex!=null && tvTitle!=null) {
            tvPagerIndex.setTypeface(typeface);
            tvTitle.setTypeface(typeface);
        }
    }

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
}
