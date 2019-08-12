package com.zenfer.network.upload;


import com.zenfer.network.bean.NetWordResult;
import com.zenfer.network.framwork.NetwordException;

/**
 * 上传监听
 *
 * @author Zenfer
 * @date 2019/6/14 9:38
 */
public interface UploadProgressListener {
    /**
     * 上传成功
     *
     * @param result 回调结果
     */
    void onSuccess(String tag, NetWordResult result);

    /**
     * 上传失败
     *
     * @param e 异常
     */
    void onFail(String tag, NetwordException e);

    /**
     * 上传开始
     */
    void onBegin();

    /**
     * 上传结束
     */
    void onEnd();

    /**
     * 上传进度
     *
     * @param currentBytesCount 当前文件上传大小
     * @param totalBytesCount   文件总大小
     */
    void onProgress(long currentBytesCount, long totalBytesCount);


}
