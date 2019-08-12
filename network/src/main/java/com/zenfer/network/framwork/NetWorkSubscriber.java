package com.zenfer.network.framwork;

import com.zenfer.network.bean.NetWordResult;

import io.reactivex.observers.DisposableObserver;

/**
 * 网络请求 Rxjava Subscriber
 *
 * @author Zenfer
 * @date 2019/6/11 11:22
 */
public class NetWorkSubscriber extends DisposableObserver<NetWordResult> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(NetWordResult t) {

    }


}
