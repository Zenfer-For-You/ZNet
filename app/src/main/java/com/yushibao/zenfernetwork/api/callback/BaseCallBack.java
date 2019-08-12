package com.yushibao.zenfernetwork.api.callback;

import com.zenfer.network.framwork.NetwordException;

public interface BaseCallBack<T> {
    /**
     * 请求成功
     *
     * @param result 返回参数
     */
    void onSuccess(String tag, T result);

    /**
     * 请求失败
     *
     * @param msg 错误信息
     */
    void onFail(String tag, NetwordException msg);

    /**
     * 请求开始
     */
    void onBegin(String tag);

    /**
     * 请求结束
     */
    void onEnd(String tag);
}
