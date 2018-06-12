# ModulesPhotoView
一个简单的图片查看模块。现在有以下的小功能：
- 图片查看（基于Glide）
- 图片下载（基于简单的Http下载）

# API
都是很简陋的API
```java
findViewById(R.id.button).setOnClickListener(view -> {
    ArrayList<PhotoBean> photoBeanArrayList = new ArrayList<>();
    photoBeanArrayList.add(new PhotoBean("家庭教师","http://f.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb051355fbb27367adab44aede042.jpg"));
    photoBeanArrayList.add(new PhotoBean("家庭教师1212","http://img.kumi.cn/photo/f3/6b/43/f36b43bf558cb176.jpg"));
    photoBeanArrayList.add(new PhotoBean("3","http://windowserl.honeybot.cn:8080/Images/yolkworld.png"));
    photoBeanArrayList.add(new PhotoBean("4","http://img.kumi.cn/photo/f6/d9/bf/f6d9bfef5cbe21b1.jpg"));
    photoBeanArrayList.add(new PhotoBean("家庭教师","fsdfsd"));
    photoBeanArrayList.add(new PhotoBean("萝莉1","http://5b0988e595225.cdn.sohucs.com/images/20171013/3ceaedb2e61f4de68aacc1d02986a587.jpeg"));
    photoBeanArrayList.add(new PhotoBean("萝莉2","http://img.hkwb.net/attachement/jpg/site2/20140901/4487fc912322156e5d3d3b.jpg"));
    PhotoConfig.getInstance()
            .setOpenDownload(true)
            .setSavePhotoCallback(new PhotoConfig.SavePhotoCallback() {
                @Override
                public void onStart(String saveSuccessFilePath) {
                    runOnUiThread(()->{
                        Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_SHORT).show();
                    });
                }
                @Override
                public void onSuccess(String saveSuccessFilePath) {
                    runOnUiThread(()->{
                        Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                    });
                }
                @Override
                public void onFail(String saveSuccessFilePath) {
                    runOnUiThread(()->{
                        Toast.makeText(MainActivity.this, "onFail", Toast.LENGTH_SHORT).show();
                    });
                }
            })
            .toViewPhoto(MainActivity.this.getApplicationContext(), photoBeanArrayList);
});
```
既然这么简陋，为啥还要放出来？
- 其实可以基于这个上面去做一个功能很全面的图片浏览器
- 或者说作为模块依赖到自己的工程里面去
- 而我做这个模块的目的，是基于此上面，做一个图片查看的组件，嗯，公司的项目要走组件化了。

# Show
最后放一个图吧
[简单看看就好了](./show/photo_show_1.gif)
