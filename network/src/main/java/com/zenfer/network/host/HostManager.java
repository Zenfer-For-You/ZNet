package com.zenfer.network.host;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 域名管理器
 *
 * @author Zenfer
 * @date 2019/6/24 16:19
 */
public class HostManager {

    /**
     * 添加 Host
     *
     * @param hostKey 域名对应的key
     * @param host    域名
     */
    public void addHost(String hostKey, @NonNull String host) {
        if (TextUtils.isEmpty(hostKey)) {
            throw new IllegalStateException("hostkey must not be null or empty");
        }
        if (TextUtils.isEmpty(host)) {
            throw new IllegalStateException("host must not be null or empty");
        }
        HOST_MAP.put(hostKey, host);
    }

    /**
     * 获取 Host
     *
     * @param hostKey 域名对应的key
     * @return 域名
     */
    public String getHost(String hostKey) {
        return HOST_MAP.get(hostKey);
    }


    private Map<String, String> HOST_MAP = new ConcurrentHashMap<>(5);

    private HostManager() {
    }

    public static HostManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final HostManager INSTANCE = new HostManager();
    }
}
