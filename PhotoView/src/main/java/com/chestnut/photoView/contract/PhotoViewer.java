package com.chestnut.photoView.contract;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/9/20 14:52
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class PhotoViewer implements IBuildPhotoViewer<PhotoViewer> {

    public PhotoViewer() {

    }

    @Override
    public PhotoViewer enableDownload() {
        return this;
    }

    @Override
    public PhotoViewer disableDownload() {
        return this;
    }

    @Override
    public PhotoViewer setTitle(String str) {
        return this;
    }

    @Override
    public void build() {

    }
}
