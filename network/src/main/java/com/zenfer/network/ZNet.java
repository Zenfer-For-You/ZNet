package com.zenfer.network;

import android.support.annotation.NonNull;

import com.zenfer.network.host.HostManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求初始化类
 *
 * @author Zenfer
 * @date 2019/8/12 16:26
 */
public class ZNet {

    /**
     * 请求头参数
     */
    private static Map<String, String> HEAD_PARAM = new HashMap<>(6);

    public static ZNet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * @param hostKey 域名对应的key
     * @param hostUrl 域名
     */
    public void addHost(@NonNull String hostKey, @NonNull String hostUrl) {
        HostManager.getInstance().addHost(hostKey, hostUrl);
    }

    /**
     * 所有接口的请求头
     */
    public Map<String, String> getHeadParam() {
        return HEAD_PARAM;
    }

    /**
     * 设置所有接口的请求头
     */
    public void setHeadParam(@NonNull String key, @NonNull String value) {
        HEAD_PARAM.put(key, value);
    }

    /**
     * 设置所有接口的请求头
     */
    public void setHeadParam(@NonNull Map<String, String> headParams) {
        HEAD_PARAM.putAll(headParams);
    }

    private ZNet() {
    }

    private static class SingletonHolder {
        private static final ZNet INSTANCE = new ZNet();
    }
}
