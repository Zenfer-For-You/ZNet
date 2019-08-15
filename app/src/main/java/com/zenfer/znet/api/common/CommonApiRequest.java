package com.zenfer.znet.api.common;


import android.support.annotation.Nullable;

import com.zenfer.network.framwork.BaseNetWorkDisposable;
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

    public static <T> void excute(@Nullable Object params, @CommonApiEnum String tag, BaseNetWorkDisposable<T> networkCallBack) {
        networkCallBack.setTag(tag);
        try {
//            CommonApiSelector.get(tag, params)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .flatMap(new Function<NetWordResult, ObservableSource<T>>() {
//                        @Override
//                        public ObservableSource<T> apply(NetWordResult result) throws Exception {
//                            try {
//                                if (result.getStatus_code() == 200) {
////                                    GsonUtil.get
////                                    return Observable.just(result);
//                                    return Observable.error(new NetwordException(null, 0, ""));
//                                } else if (result.getStatus_code() == 666) {
//                                    //请求失败,需要解析data的数据
//                                    String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
//                                    int errorCode = result.getStatus_code();
//                                    return Observable.error(new NetwordException(null, errorCode, message));
//                                } else {
//                                    //请求失败
//                                    String message = !TextUtils.isEmpty(result.getMessage()) ? result.getMessage() : "未知错误";
//                                    int errorCode = result.getStatus_code();
//                                    return Observable.error(new NetwordException(null, errorCode, message));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return Observable.error(e);
//                            }
//                        }
//                    })
//                    .subscribe(networkCallBack);
//            ZNetRxUtils.getInstance().addDisposable(networkCallBack);
//            ZNetwork.addObservable(CommonApiSelector.get(tag, params), networkCallBack);
        } catch (Exception e) {
            networkCallBack.onError(e);
        }
    }


}
