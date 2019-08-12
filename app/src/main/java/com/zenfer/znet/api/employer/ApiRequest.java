package com.zenfer.znet.api.employer;


import android.support.annotation.Nullable;

import com.zenfer.network.framwork.ZNetwork;
import com.zenfer.znet.api.callback.BaseCallBack;
import com.zenfer.znet.api.callback.NetworkCallBack;

import static com.zenfer.znet.api.NetCommonParams.commonObjectParam;

/**
 * 网络请求方法
 *
 * @author Zenfer
 * @date 2019/6/11 16:10
 */
public class ApiRequest {
    /**
     * 招工单位列表
     */
    public static void getCompanyList(BaseCallBack callBack) {
        excute(commonObjectParam(), ApiEnum.COMPANY_LIST, new NetworkCallBack(callBack));
    }


    public static void excute(@Nullable Object params, @ApiEnum String tag, NetworkCallBack networkCallBack) {
        networkCallBack.setTag(tag);
        try {
            ZNetwork.addObservable(Api.get(tag, params), networkCallBack);
        } catch (Exception e) {
            networkCallBack.onError(e);
        }
    }

}
