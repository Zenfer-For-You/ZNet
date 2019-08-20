package com.zenfer.znet.api.callback;

import android.text.TextUtils;

import com.zenfer.annotation.ZNetConvert;
import com.zenfer.network.error.NetwordException;
import com.zenfer.znet.api.HostEnum;
import com.zenfer.znet.bean.NetWordResult;

import io.reactivex.functions.Function;

@ZNetConvert({HostEnum.HOST_COMMON, HostEnum.HOST_EMPLOYER})
public class NetworkResultFunction<T> implements Function<NetWordResult<T>, T> {
    @Override
    public T apply(NetWordResult<T> result) throws Exception {
        if (result.getStatus_code() == 200) {
            return result.getData();
        } else if (result.getStatus_code() == 666) {
            //请求失败,需要解析data的数据
            String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
            int errorCode = result.getStatus_code();
            throw new NetwordException(null, errorCode, message);
        } else {
            //请求失败
            String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
            int errorCode = result.getStatus_code();
            throw new NetwordException(null, errorCode, message);
        }
    }
}
