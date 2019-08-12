package com.zenfer.network.framwork;

import io.reactivex.observers.DisposableObserver;

/**
 * 网络请求 Rxjava Subscriber
 *
 * @author Zenfer
 * @date 2019/6/11 11:22
 */
public abstract class BaseNetWorkDisposable<T> extends DisposableObserver<T> {

    /**
     * 接口标签
     */
    protected String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
