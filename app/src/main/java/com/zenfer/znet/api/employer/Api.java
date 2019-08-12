package com.zenfer.znet.api.employer;

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
public class Api {

    /**
     * 获取对应的 Observable<NetWordResult>
     *
     * @param tag    接口标签{@link ApiEnum}
     * @param params 参数
     * @return 请求接口的Observable
     */
    public static Observable<NetWordResult> get(@ApiEnum String tag, Object params) throws Exception {
        switch (tag) {
            case ApiEnum.COMPANY_LIST:
                return ZNetwork.getInstance().getApi(ApiService.class).getCompanyList(RequestBodyUtil.createMapParams(params));
            case ApiEnum.COMPANY_DELETE:
                return ZNetwork.getInstance().getApi(ApiService.class).deleteCompany(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.BUILD_NEW_ORDER_SAVE:
                return ZNetwork.getInstance().getApi(ApiService.class).saveCompanyInfo(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.EDIT_NEW_ORDER_INFO:
                return ZNetwork.getInstance().getApi(ApiService.class).getOrderInfo(RequestBodyUtil.createMapParams(params));
            case ApiEnum.ORDER_CONFIG:
                return ZNetwork.getInstance().getApi(ApiService.class).orderConfig(RequestBodyUtil.createMapParams(params));
            case ApiEnum.ORDER_COMMIT:
                return ZNetwork.getInstance().getApi(ApiService.class).commitOrder(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.MAP_MARKER:
                return ZNetwork.getInstance().getApi(ApiService.class).mapMarker(RequestBodyUtil.createMapParams(params));
            case ApiEnum.ORDER_LIST:
                return ZNetwork.getInstance().getApi(ApiService.class).getOrderList(RequestBodyUtil.createMapParams(params));
            case ApiEnum.ORDER_DETAIL:
                return ZNetwork.getInstance().getApi(ApiService.class).getOrderDetail(RequestBodyUtil.createMapParams(params));
            case ApiEnum.PAY_ORDER_INFO:
                return ZNetwork.getInstance().getApi(ApiService.class).getPayOrderInfo(RequestBodyUtil.createMapParams(params));
            case ApiEnum.EMPLOYEE_LIST:
                return ZNetwork.getInstance().getApi(ApiService.class).getEmployeeList(RequestBodyUtil.createMapParams(params));
            case ApiEnum.OFFER_EMPLOYEES:
                return ZNetwork.getInstance().getApi(ApiService.class).offerEmployees(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.REFUSE_EMPLOYEE:
                return ZNetwork.getInstance().getApi(ApiService.class).refuseEmployee(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.ORDER_CANCEL:
                return ZNetwork.getInstance().getApi(ApiService.class).orderCancel(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.ORDER_REFUND:
                return ZNetwork.getInstance().getApi(ApiService.class).orderRefund(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.KEEP_MATCHING:
                return ZNetwork.getInstance().getApi(ApiService.class).keepMatching(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.SETTLEMENT_BUCKLE:
                return ZNetwork.getInstance().getApi(ApiService.class).settlementBuckle(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.SETTLEMENT_DISMISS:
                return ZNetwork.getInstance().getApi(ApiService.class).settlementDismiss(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.SETTLEMENT_FULL:
                return ZNetwork.getInstance().getApi(ApiService.class).settlementFull(RequestBodyUtil.createMapRequestBody(params));
            case ApiEnum.SETTLEMENT_INFO:
                return ZNetwork.getInstance().getApi(ApiService.class).settlementInfo(RequestBodyUtil.createMapParams(params));
            case ApiEnum.REISSUE_SALARY_LIST:
                return ZNetwork.getInstance().getApi(ApiService.class).reissueSalaryList(RequestBodyUtil.createMapParams(params));
            case ApiEnum.REISSUE_SALARY_DETAIL:
                return ZNetwork.getInstance().getApi(ApiService.class).reissueSalaryDetail(RequestBodyUtil.createMapParams(params));
            case ApiEnum.REISSUE_SALARY_DEAL:
                return ZNetwork.getInstance().getApi(ApiService.class).dealReissueSalary(RequestBodyUtil.createMapRequestBody(params));

            default:
                throw new Exception("can not match the request tag \"" + tag + "\"");
        }
    }

}
