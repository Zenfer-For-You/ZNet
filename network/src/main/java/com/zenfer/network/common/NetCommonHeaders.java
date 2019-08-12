package com.zenfer.network.common;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共头部参数
 *
 * @author Zenfer
 * @date 2019/6/11 10:21
 */
public class NetCommonHeaders {
    private static Map<String, String> HEAD_PARAM = new HashMap<>(6);

    /**
     * 所有接口的请求头
     */
    public static Map<String, String> commonParam() {
        return HEAD_PARAM;
    }

    public static void setHeadParam(@NonNull String key, @NonNull String value) {
        HEAD_PARAM.put(key, value);
    }
}
