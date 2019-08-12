package com.zenfer.network.download;


import com.zenfer.network.error.NetwordException;
import com.zenfer.network.error.NetworkErrorHandler;

import java.io.File;

import io.reactivex.observers.DisposableObserver;

import static com.zenfer.network.error.NetworkErrorCode.ERROR_CODE_DOWNLOAD;


/**
 * 下载回调
 *
 * @author Zenfer
 * @date 2019/6/14 9:37
 */
public class DownloadCallBack extends DisposableObserver<File> {

    private DownloadProgressListener listener;

    public DownloadCallBack(DownloadProgressListener listener) {
        this.listener = listener;
    }

    public DownloadProgressListener getListener() {
        return listener;
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
                listener.onFail(NetworkErrorHandler.getException(e));
                listener.onEnd();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onNext(File result) {
        if (result != null && result.exists() && result.getTotalSpace() > 0) {
            if (listener != null) {
                listener.onSuccess(result);
            }
        } else {
            if (listener != null) {
                listener.onFail(new NetwordException(null, ERROR_CODE_DOWNLOAD, "文件下载失败"));
            }
        }
    }
}
