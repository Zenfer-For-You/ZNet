package com.zenfer.znet.api.callback;

import android.support.annotation.Nullable;

import com.zenfer.annotation.ZNetCallBack;
import com.zenfer.network.error.NetworkErrorHandler;
import com.zenfer.network.framwork.BaseNetWorkDisposable;
import com.zenfer.znet.api.HostEnum;

/**
 * 继承 BaseNetWorkDisposable 实现业务的接口回调监听
 *
 * @author Zenfer
 * @date 2019/8/12 10:51
 */
@ZNetCallBack({HostEnum.HOST_COMMON, HostEnum.HOST_EMPLOYER})
public class NetworkCallBack<T> extends BaseNetWorkDisposable<T> {

    private BaseCallBack<T> callBack;

    public NetworkCallBack(@Nullable BaseCallBack<T> callBack) {
        this.callBack = callBack;
    }


    @Override
    public void onStart() {
        if (callBack != null) {
            callBack.onBegin(tag);
        }
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onEnd(tag);
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (callBack != null) {
                callBack.onFail(tag, NetworkErrorHandler.getException(e));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onNext(T result) {
        // 此部分需要根据项目具体需求重新定义
        if (callBack != null) {
            callBack.onSuccess(tag, result);
        }
    }
}
