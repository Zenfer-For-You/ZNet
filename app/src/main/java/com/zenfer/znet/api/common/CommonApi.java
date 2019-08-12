package com.zenfer.znet.api.common;

import com.zenfer.znet.bean.NetWordResult;
import com.zenfer.network.framwork.ZNetwork;
import com.zenfer.network.framwork.RequestBodyUtil;

import io.reactivex.Observable;

/**
 * 获取 api 请求的 Observable<NetWordResult> 对象
 *
 * @author Zenfer
 * @date 2019/6/11 15:20
 */
public class CommonApi {

    /**
     * 获取对应的 Observable<NetWordResult>
     *
     * @param tag    接口标签{@link CommonApiEnum}
     * @param params 参数
     * @return 请求接口的Observable
     */
    public static Observable<NetWordResult> get(@CommonApiEnum String tag, Object params) throws Exception {
        switch (tag) {
            case CommonApiEnum.GET_CODE_LOGIN:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getLoginCode(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.LOGIN:
                return ZNetwork.getInstance().getApi(CommonApiService.class).login(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.REGISTER:
                return ZNetwork.getInstance().getApi(CommonApiService.class).register(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.CONFIG:
                return ZNetwork.getInstance().getApi(CommonApiService.class).config(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.AUTH:
                return ZNetwork.getInstance().getApi(CommonApiService.class).auth(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.BANDCARD_CARD:
                return ZNetwork.getInstance().getApi(CommonApiService.class).bandCard(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.GET_MY_INFO:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getUserInfo(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.GET_BANNER:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getBannerList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.CHANGE_NICKNAME:
                return ZNetwork.getInstance().getApi(CommonApiService.class).changeNockname(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.CHANGE_PHONE:
                return ZNetwork.getInstance().getApi(CommonApiService.class).changePhone(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.PAY_ORDER:
                return ZNetwork.getInstance().getApi(CommonApiService.class).payOrder(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.COMPANY_AUTHENTICATION:
                return ZNetwork.getInstance().getApi(CommonApiService.class).companyAuthentication(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.GET_COMPANY_INFO:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getCompanyInfo(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.REVENUE_AND_EXPENDITURE:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getRevenueAndExpenditureList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.WITHDRAW_LIST:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getWithdrawList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.RECHARGE_LIST:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getRechargeList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.WITHDRAW_INFO:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getWithdrawInfo(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.WITHDRAW:
                return ZNetwork.getInstance().getApi(CommonApiService.class).withdraw(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.RECHARGE:
                return ZNetwork.getInstance().getApi(CommonApiService.class).recharge(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.SET_PWD:
                return ZNetwork.getInstance().getApi(CommonApiService.class).setPwd(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.CHECK_USER:
                return ZNetwork.getInstance().getApi(CommonApiService.class).checkUser(RequestBodyUtil.createMapRequestBody(params));
            case CommonApiEnum.CHECK_VERSION:
                return ZNetwork.getInstance().getApi(CommonApiService.class).checkUpgrade(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.INVITE_LIST:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getInviteList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.INVITE_DETAIL_LIST:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getInviteDetailList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.INVITE_SECOND_LIST:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getInviteSecondList(RequestBodyUtil.createMapParams(params));
            case CommonApiEnum.INVITE_RQ:
                return ZNetwork.getInstance().getApi(CommonApiService.class).getInviteRQ(RequestBodyUtil.createMapParams(params));
            default:
                throw new Exception("can not match the request tag \"" + tag + "\"");
        }
    }

}
