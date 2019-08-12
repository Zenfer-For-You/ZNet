package com.yushibao.zenfernetwork.api.upload;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yushibao.zenfernetwork.bean.NetWordResult;
import com.zenfer.network.framwork.BaseNetWorkDisposable;
import com.zenfer.network.framwork.NetwordException;
import com.zenfer.network.framwork.NetworkErrorHandler;
import com.zenfer.network.upload.UploadProgressListener;

/**
 * 继承 BaseNetWorkDisposable 实现上传业务的接口回调监听
 *
 * @author Zenfer
 * @date 2019/8/12 10:51
 */
public class UploadCallBack extends BaseNetWorkDisposable<NetWordResult> {

    private UploadProgressListener<NetWordResult> listener;

    public UploadCallBack(@Nullable UploadProgressListener<NetWordResult> listener) {
        this.listener = listener;
    }

    public UploadProgressListener getListener() {
        return listener;
    }

    public void setListener(UploadProgressListener<NetWordResult> listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        if (listener != null) {
            listener.onBegin();
        }
    }

    @Override
    public void onComplete() {
        if (listener != null) {
            listener.onEnd();
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (listener != null) {
                listener.onFail(tag, NetworkErrorHandler.getException(e));
                listener.onEnd();
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
                if (listener != null) {
                    listener.onSuccess(tag, result);
                }
            } else {
                //请求失败
                String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
                int errorCode = 0;
                if (listener != null) {
                    listener.onFail(tag, new NetwordException(null, errorCode, message));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (listener != null) {
                listener.onEnd();
            }
        }
    }
}
