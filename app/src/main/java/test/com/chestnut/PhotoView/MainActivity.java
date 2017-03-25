package test.com.chestnut.PhotoView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chestnut.PhotoView.PhotoBean;
import com.chestnut.PhotoView.PhotoViewActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PhotoBean> photoBeanArrayList = new ArrayList<>();
                photoBeanArrayList.add(new PhotoBean("家庭教师","http://f.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb051355fbb27367adab44aede042.jpg",null,false,null));
                photoBeanArrayList.add(new PhotoBean("家庭教师1212","http://img.kumi.cn/photo/f3/6b/43/f36b43bf558cb176.jpg",null,false,null));
                photoBeanArrayList.add(new PhotoBean("3","http://windowserl.honeybot.cn:8080/Images/yolkworld.png",null,false,null));
                photoBeanArrayList.add(new PhotoBean("4","http://img.kumi.cn/photo/f6/d9/bf/f6d9bfef5cbe21b1.jpg",null,false,null));
                photoBeanArrayList.add(new PhotoBean("家庭教师","fsdfsd",null,false,null));

                Intent intent = new Intent(MainActivity.this,PhotoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(PhotoViewActivity.PHOTO_LIST_KEY,photoBeanArrayList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
