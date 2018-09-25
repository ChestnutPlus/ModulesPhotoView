# ModulesPhotoView
一个简单的图片查看模块。现在有以下的小功能：
- 图片查看（基于Glide）
- 图片下载（基于简单的Http下载）

# API
都是很简单的API
```java
PhotoViewer.builder()
        .setPhotoBeanArrayList(photoBeanArrayList)
        .setPhotoBeanArrayList(testPhotoBeanArrayList)
        .setPhotoBean(testPhotoBean)
        .setPhotoBean(photoBean)
        .setPhotoBean(new PhotoBean("鬼刀","https://b-ssl.duitang.com/uploads/item/201608/30/20160830122907_iyLwx.jpeg"))
        .setPhotoBean(new PhotoBean("鬼刀2","https://i2.fuimg.com/510372/38c3e5eac4190188.jpg"))
        .setPhotoBean(new PhotoBean("鬼刀3","/sdcard/_12.jpg"))
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
```
为啥还要写这么简单的一个Demo？
- 其实可以基于这个上面去做一个功能很全面的图片浏览器
- 或者说作为模块依赖到自己的工程里面去
- 而我做这个模块的目的，是基于此上面，做一个图片查看的组件，嗯，公司的项目要走组件化了。

# Show
最后放一个图吧
![](./show/photo_show_1.gif)
