package com.zenfer.znet.api.callback;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zenfer.znet.bean.NetWordResult;
import com.zenfer.network.framwork.BaseNetWorkDisposable;
import com.zenfer.network.error.NetwordException;
import com.zenfer.network.error.NetworkErrorHandler;

/**
 * 继承 BaseNetWorkDisposable 实现业务的接口回调监听
 *
 * @author Zenfer
 * @date 2019/8/12 10:51
 */
public class NetworkCallBack extends BaseNetWorkDisposable<NetWordResult> {

    private BaseCallBack callBack;

    public NetworkCallBack(@Nullable BaseCallBack callBack) {
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
                callBack.onEnd(tag);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onNext(NetWordResult result) {
        // 此部分需要根据项目具体需求重新定义
        try {
            if (result.getStatus_code() == 200) {
                if (callBack != null) {
                    callBack.onSuccess(tag, result);
                }
            } else if (result.getStatus_code() == 666) {
                //请求失败,需要解析data的数据
                String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
                int errorCode = result.getStatus_code();
                if (callBack != null) {
                    callBack.onFail(tag, new NetwordException(null, errorCode, message, result.getData()));
                }
            } else {
                //请求失败
                String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
                int errorCode = result.getStatus_code();
                if (callBack != null) {
                    callBack.onFail(tag, new NetwordException(null, errorCode, message));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (callBack != null) {
                callBack.onEnd(tag);
            }
        }
    }
}
