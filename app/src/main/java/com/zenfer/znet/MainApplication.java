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
        ZNet.getInstance().setHeadParam("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9jb21tb24udGVzdC55c2Jqb2IuY29tXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE1NjU4NTM1OTQsIm5iZiI6MTU2NTg1MzU5NCwianRpIjoiRmg0S1pJYkpuNHBvaTNTOSIsInN1YiI6MywicHJ2IjoiZjY0ZDQ4YTZjZWM3YmRmYTdmYmY4OTk0NTRiNDg4YjNlNDYyNTIwYSIsInJvbGUiOiIxIn0.ry9_FJmsdmFtGnevVk3XhhGuf1Ps3RIfLhXPKsH5FdY");
    }
}
