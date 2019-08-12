package com.zenfer.znet;

import android.app.Application;

import com.zenfer.znet.api.HostEnum;
import com.zenfer.network.ZNet;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZNet.getInstance().addHost(HostEnum.HOST_COMMON, "http://common.test.ysbjob.com");
        ZNet.getInstance().addHost(HostEnum.HOST_EMPLOYER, "http://er.test.ysbjob.com");
        ZNet.getInstance().setHeadParam("channel", "android");
        ZNet.getInstance().setHeadParam("role", "1");
    }
}
