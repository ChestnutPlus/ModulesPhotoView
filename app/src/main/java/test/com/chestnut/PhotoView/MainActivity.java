package test.com.chestnut.PhotoView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chestnut.common.manager.FontManager;
import com.chestnut.common.manager.imgloader.ImgLoaderManager;
import com.chestnut.photoView.bean.PhotoBean;
import com.chestnut.photoView.contract.PhotoViewer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String FONT_PATH_WA_WA = "font/W5.TTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImgLoaderManager.getInstance().init();
        FontManager.getInstance().init(this);
        findViewById(R.id.button).setOnClickListener(view -> {

            ArrayList<PhotoBean> photoBeanArrayList = new ArrayList<>();
            photoBeanArrayList.add(new PhotoBean("家庭教师","http://f.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb051355fbb27367adab44aede042.jpg"));
            photoBeanArrayList.add(new PhotoBean("家庭教师1212","http://img.kumi.cn/photo/f3/6b/43/f36b43bf558cb176.jpg"));
            photoBeanArrayList.add(new PhotoBean("","http://windowserl.honeybot.cn:8080/Images/yolkworld.png"));
            photoBeanArrayList.add(new PhotoBean("4","http://img.kumi.cn/photo/f6/d9/bf/f6d9bfef5cbe21b1.jpg"));
            photoBeanArrayList.add(new PhotoBean("家庭教师","fsdfsd"));
            photoBeanArrayList.add(new PhotoBean("萝莉1","http://5b0988e595225.cdn.sohucs.com/images/20171013/3ceaedb2e61f4de68aacc1d02986a587.jpeg"));
            photoBeanArrayList.add(new PhotoBean("萝莉2","http://img.hkwb.net/attachement/jpg/site2/20140901/4487fc912322156e5d3d3b.jpg"));

            ArrayList<TestPhotoBean> testPhotoBeanArrayList = new ArrayList<>();
            testPhotoBeanArrayList.add(new TestPhotoBean());
            testPhotoBeanArrayList.add(new TestPhotoBean());
            testPhotoBeanArrayList.add(new TestPhotoBean());

            TestPhotoBean testPhotoBean = new TestPhotoBean();
            PhotoBean photoBean = new PhotoBean("家庭教师","http://f.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb051355fbb27367adab44aede042.jpg");

            PhotoViewer.builder()
                    .setPhotoBeanArrayList(photoBeanArrayList)
                    .setPhotoBeanArrayList(testPhotoBeanArrayList)
                    .setPhotoBean(testPhotoBean)
                    .setPhotoBean(photoBean)
                    .setEnableDownload(true)
                    .setTypeface(FontManager.getInstance().get(FONT_PATH_WA_WA))
                    .setPhotoViewerCallback(new PhotoViewer.Callback(){
                        @Override
                        public void onSaveSuccess(String saveSuccessFilePath) {
                            runOnUiThread(()->{
                                Toast.makeText(MainActivity.this, "onSaveSuccess", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onSaveFail(String saveSuccessFilePath) {
                            runOnUiThread(()->{
                                Toast.makeText(MainActivity.this, "onSaveFail", Toast.LENGTH_SHORT).show();
                            });
                        }
                    })
                    .build(this);
        });

    }
}
