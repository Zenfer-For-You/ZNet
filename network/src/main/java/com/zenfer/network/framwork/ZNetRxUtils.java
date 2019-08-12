package com.zenfer.network.framwork;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Rx工具类,为了控制当前页面的网络请求
 *
 * @author Zenfer
 * @date 2019/6/11 11:23
 */
public class ZNetRxUtils {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ZNetRxUtils() {
    }

    public static ZNetRxUtils getInstance() {
        return new ZNetRxUtils();
    }

    /**
     * 清除所有网络请求
     */
    public void clearDisposable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    /**
     * 清除当前界面的网络请求
     */
    public void disposed() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 添加网络请求
     */
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

}
