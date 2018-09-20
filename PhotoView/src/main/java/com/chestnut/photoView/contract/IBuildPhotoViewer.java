package com.chestnut.photoView.contract;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2018/9/20 14:50
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public interface IBuildPhotoViewer<T> {
    T enableDownload();
    T disableDownload();
    T setTitle(String str);
    void build();
}
