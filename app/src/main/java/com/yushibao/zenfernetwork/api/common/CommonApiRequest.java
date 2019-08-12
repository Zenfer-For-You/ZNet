package com.yushibao.zenfernetwork.api.common;


import android.support.annotation.Nullable;

import com.yushibao.zenfernetwork.api.callback.NetworkCallBack;
import com.zenfer.network.framwork.Network;

import java.util.Map;

import static com.zenfer.network.common.NetCommonParams.commonObjectParam;
import static com.zenfer.network.common.NetCommonParams.commonParam;


/**
 * 网络请求方法
 *
 * @author Zenfer
 * @date 2019/6/11 16:10
 */
public class CommonApiRequest {
    /**
     * 提现
     */
    public static void withdraw(double money, String payPWD, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("money", String.valueOf(money));
        map.put("payPWD", payPWD);
        excute(map, CommonApiEnum.WITHDRAW, netWorkCallBack);
    }

    /**
     * 提现费用获取
     */
    public static void getWithdrawInfo(NetworkCallBack netWorkCallBack) {
        excute(commonObjectParam(), CommonApiEnum.WITHDRAW_INFO, netWorkCallBack);
    }

    /**
     * 充值记录
     */
    public static void getRechargeList(int page, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("page", String.valueOf(page));
        excute(map, CommonApiEnum.RECHARGE_LIST, netWorkCallBack);
    }

    /**
     * 提现记录
     */
    public static void getWithdrawList(int page, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("page", String.valueOf(page));
        excute(map, CommonApiEnum.WITHDRAW_LIST, netWorkCallBack);
    }

    /**
     * 收支明细
     */
    public static void getRevenueAndExpenditureList(int page, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("page", String.valueOf(page));
        excute(map, CommonApiEnum.REVENUE_AND_EXPENDITURE, netWorkCallBack);
    }

    /**
     * 获取登录验证码
     */
    public static void auth(String name, String idCard, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("name", name);
        map.put("idCard", idCard);
        excute(map, CommonApiEnum.AUTH, netWorkCallBack);
    }

    /**
     * 获取登录验证码
     * mobile 手机
     * type 类型，不传时，表示注册，bind :绑卡，changePhone:修改手机号，loginOrReg:注册或登录
     */
    public static void getMsgCode(String mobile, String type, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("mobile", mobile);
        map.put("type", type);
        excute(map, CommonApiEnum.GET_CODE_LOGIN, netWorkCallBack);
    }

    /**
     * 登录
     */
    public static void codeLogin(String mobile, String code, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("mobile", mobile);
        map.put("code", code);
//        map.put("lat", "");
//        map.put("lon", "");
        excute(map, CommonApiEnum.LOGIN, netWorkCallBack);
    }

    /**
     * 注册
     */
    public static void register(String mobile, String code, String invite, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("inviteCode", invite);
        excute(map, CommonApiEnum.REGISTER, netWorkCallBack);
    }

    /**
     * 绑定银行卡
     */
    public static void bandCard(String cardNum, String phone, String code, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("card", cardNum);
        map.put("phone", phone);
        map.put("code", code);
        excute(map, CommonApiEnum.BANDCARD_CARD, netWorkCallBack);
    }

    /**
     * 获取个人信息
     *
     * @param netWorkCallBack
     */
    public static void getMyInfo(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.GET_MY_INFO, netWorkCallBack);
    }

    /**
     * 修改昵称
     */
    public static void changeNickname(String name, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("nickName", name);
        excute(map, CommonApiEnum.CHANGE_NICKNAME, netWorkCallBack);
    }

    /**
     * 获取充值信息
     */
    public static void getPayInfo(double money, int payWay, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("amount", String.valueOf(money));
        map.put("payWay", String.valueOf(payWay));
        excute(map, CommonApiEnum.RECHARGE, netWorkCallBack);
    }

    /**
     * 基本参数配置
     *
     * @param netWorkCallBack 回调
     */
    public static void config(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.CONFIG, netWorkCallBack);
    }

    public static void getBanner(String place, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("place", place);
        excute(map, CommonApiEnum.GET_BANNER, netWorkCallBack);
    }

    public static void payOrder(String amount, int payWay, String payFor, int days, String payPassword, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("amount", amount);
        map.put("payWay", payWay);
        map.put("payFor", payFor);
        map.put("days", days);
        map.put("payPassword", payPassword);
        excute(map, CommonApiEnum.PAY_ORDER, netWorkCallBack);
    }

    /**
     * 修改昵称
     */
    public static void changePhone(String oldPhone, String newPhone, String code, NetworkCallBack netWorkCallBack) {
        Map<String, String> map = commonParam();
        map.put("oldPhone", oldPhone);
        map.put("newPhone", newPhone);
        map.put("code", code);
        excute(map, CommonApiEnum.CHANGE_PHONE, netWorkCallBack);
    }

    /**
     * 设置密码
     */
    public static void setPwd(String pwd, String rePwd, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("pwd", pwd);
        map.put("rePwd", rePwd);
        excute(map, CommonApiEnum.SET_PWD, netWorkCallBack);
    }

    /**
     * 身份验证
     */
    public static void checkUser(String name, String idCard, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("name", name);
        map.put("idCard", idCard);
        excute(map, CommonApiEnum.CHECK_USER, netWorkCallBack);
    }

    /**
     * 检测更新
     */
    public static void checkUpgrade(String version, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("version", version);
        excute(map, CommonApiEnum.CHECK_VERSION, netWorkCallBack);
    }


    /**
     * 获取企业信息
     *
     * @param netWorkCallBack
     */
    public static void getCompanyInfo(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.GET_COMPANY_INFO, netWorkCallBack);
    }

    /**
     * 已邀好友
     *
     * @param netWorkCallBack
     */
    public static void getInviteList(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.INVITE_LIST, netWorkCallBack);
    }

    /**
     * 奖励明细
     *
     * @param netWorkCallBack
     */
    public static void getInviteDetail(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.INVITE_DETAIL_LIST, netWorkCallBack);
    }

    /**
     * 邀请页二维码
     *
     * @param netWorkCallBack
     */
    public static void getInviteRQ(NetworkCallBack netWorkCallBack) {
        excute(null, CommonApiEnum.INVITE_RQ, netWorkCallBack);
    }

    public static void getInviteSecondList(int invited_uid, NetworkCallBack netWorkCallBack) {
        Map<String, Object> map = commonObjectParam();
        map.put("invited_uid", invited_uid);
        excute(map, CommonApiEnum.INVITE_SECOND_LIST, netWorkCallBack);
    }

    public static void excute(@Nullable Object params, @CommonApiEnum String tag, NetworkCallBack networkCallBack) {
        networkCallBack.setTag(tag);
        try {
            Network.addObservable(CommonApi.get(tag, params), networkCallBack);
        } catch (Exception e) {
            networkCallBack.onError(e);
        }
    }


}
