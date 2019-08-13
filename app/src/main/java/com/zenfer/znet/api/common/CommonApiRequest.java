package com.zenfer.znet.api.common;


import android.support.annotation.Nullable;

import com.zenfer.network.framwork.ZNetwork;
import com.zenfer.znet.api.callback.NetworkCallBack;

import java.util.Map;

import static com.zenfer.znet.api.NetCommonParams.commonObjectParam;


/**
 * 网络请求方法
 *
 * @author Zenfer
 * @date 2019/6/11 16:10
 */
public class CommonApiRequest {

    /**
     * 登录
     */
    public static void codeLogin(String mobile, String code, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("mobile", mobile);
        map.put("code", code);
        excute(map, CommonApiEnum.LOGIN, netWorkCallBack);
    }

    /**
     * 基本参数配置
     *
     * @param netWorkCallBack 回调
     */
    public static void config(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.CONFIG, netWorkCallBack);
    }

    public static void excute(@Nullable Object params, @CommonApiEnum String tag, NetworkCallBack networkCallBack) {
        networkCallBack.setTag(tag);
        try {
            ZNetwork.addObservable(CommonApiSelector.get(tag, params), networkCallBack);
        } catch (Exception e) {
            networkCallBack.onError(e);
        }
    }


}
