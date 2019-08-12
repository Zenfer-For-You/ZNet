package com.yushibao.zenfernetwork.api.employer;


import android.support.annotation.Nullable;

import com.yushibao.zenfernetwork.api.callback.NetworkCallBack;
import com.yushibao.zenfernetwork.api.callback.BaseCallBack;
import com.zenfer.network.framwork.Network;

import static com.zenfer.network.common.NetCommonParams.commonParam;

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
        excute(commonParam(), ApiEnum.COMPANY_LIST, new NetworkCallBack(callBack));
    }


    public static void excute(@Nullable Object params, @ApiEnum String tag, NetworkCallBack networkCallBack) {
        networkCallBack.setTag(tag);
        try {
            Network.addObservable(Api.get(tag, params), networkCallBack);
        } catch (Exception e) {
            networkCallBack.onError(e);
        }
    }

}
