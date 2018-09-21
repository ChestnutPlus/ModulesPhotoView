package test.com.chestnut.PhotoView;

import com.chestnut.photoView.bean.PhotoBean;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/9/21 11:13
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class TestPhotoBean extends PhotoBean{

    private static final String[] urls = {
            "http://f.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb051355fbb27367adab44aede042.jpg",
            "http://img.kumi.cn/photo/f3/6b/43/f36b43bf558cb176.jpg",
            "http://windowserl.honeybot.cn:8080/Images/yolkworld.png",
            "http://img.kumi.cn/photo/f6/d9/bf/f6d9bfef5cbe21b1.jpg",
            "",
            "",
            "http://5b0988e595225.cdn.sohucs.com/images/20171013/3ceaedb2e61f4de68aacc1d02986a587.jpeg",
            "http://img.hkwb.net/attachement/jpg/site2/20140901/4487fc912322156e5d3d3b.jpg",
    };

    public TestPhotoBean() {
        title = "" + System.currentTimeMillis();
        url = urls[(int) (System.nanoTime()%urls.length)];
    }

    public TestPhotoBean(String title, String url) {
        super(title, url);
    }
}
