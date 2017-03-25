package com.chestnut.PhotoView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity {

    /*  常量
    * */
    public static String PHOTO_LIST_KEY = "PHOTO_LIST_KEY";
    private final String TAG = "PhotoViewActivity";
    private TextView txtPagerIndex;
    private TextView txtTitle;
    private ArrayList<PhotoBean> photoBeanArrayList;
    private int index = -1;
    private CommonUtils commonUtils = new CommonUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonUtils.hideStatusBar(this);
        Log.i(TAG,"onCreate:");
        setContentView(R.layout.activity_photo_view);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        txtPagerIndex = (TextView) findViewById(R.id.txtPagerIndex);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        //尝试取出：图片bean
        try {
            photoBeanArrayList = getIntent().getExtras().getParcelableArrayList(PHOTO_LIST_KEY);
            if (photoBeanArrayList !=null) {
                for (PhotoBean p :
                        photoBeanArrayList) {
                    Log.i(TAG, p.toString());
                }
            }
            if (photoBeanArrayList != null && photoBeanArrayList.size()>0) {
                LayoutInflater inflater = getLayoutInflater();
                ArrayList<View> views = new ArrayList<>();
                for (int i = 0; i < photoBeanArrayList.size(); i++) {
                    views.add(inflater.inflate(R.layout.pager_photo_view, null));
                }
                PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(views, photoBeanArrayList);
                viewPager.setAdapter(photoViewPagerAdapter);
                txtPagerIndex.setText("1/"+photoBeanArrayList.size());
                String title = photoBeanArrayList.get(0).title;
                txtTitle.setText(title == null ? "" : title);
                photoViewPagerAdapter.loadImg(this);
                index = 0;
            }
        } catch (Exception e) {
            txtPagerIndex.setText("0/0");
            Log.e(TAG,e.getMessage());
        }
        //设置ViewPager的翻页监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtPagerIndex.setText((position+1)+"/"+viewPager.getAdapter().getCount());
                String title = photoBeanArrayList.get(position).title;
                txtTitle.setText(title == null ? "" : title);
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //imgBack
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //save photo
        findViewById(R.id.imgDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"imgDownload:"+"save");
                if (photoBeanArrayList!=null && photoBeanArrayList.size()>0 && index!=-1) {
                    final PhotoBean photoBean = photoBeanArrayList.get(index);
                    if (photoBean.isSaveed) {
                        Toast.makeText(PhotoViewActivity.this.getApplicationContext(), PhotoLoaderConfig.DownImgSuccess+"/"+photoBean.title+".png",Toast.LENGTH_LONG).show();
                    }
                    else if (!photoBean.isSaveing && photoBean.bitmap!=null){
                        photoBean.isSaveing = true;
                        Toast.makeText(PhotoViewActivity.this.getApplicationContext(), PhotoLoaderConfig.DownImgBegin,Toast.LENGTH_LONG).show();
                        commonUtils.save(photoBean.bitmap, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + photoBean.title + ".png"), new CommonUtils.SavePhotoCallBack() {
                            @Override
                            public void saveSuccess(final File file) {
                                PhotoViewActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PhotoViewActivity.this.getApplicationContext(), PhotoLoaderConfig.DownImgSuccess+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                photoBean.isSaveing = false;
                                photoBean.isSaveed = true;
                                photoBean.bitmap = null;
                            }

                            @Override
                            public void saveFail(final File file) {
                                PhotoViewActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PhotoViewActivity.this.getApplicationContext(), PhotoLoaderConfig.DownImgFail+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                photoBean.isSaveing = false;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonUtils.release();
        Log.i(TAG,"onDestroy:");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG,"onConfigurationChanged:");
        super.onConfigurationChanged(newConfig);
    }
}
