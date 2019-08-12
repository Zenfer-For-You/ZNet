package com.zenfer.network.error;

import android.support.annotation.IntDef;

/**
 * 网络请求错误码
 *
 * @author Zenfer
 * @date 2019/6/13 10:58
 */
@IntDef({
        NetworkErrorCode.ERROR_CODE_UNKNOWN,
        NetworkErrorCode.ERROR_CODE_JSON_PARSE,
        NetworkErrorCode.ERROR_CODE_CONNECT_FAIL,
        NetworkErrorCode.ERROR_CODE_SSL_HANDSHAKE,
        NetworkErrorCode.ERROR_CODE_CERT_PATH_INVALID,
        NetworkErrorCode.ERROR_CODE_SSL_PEER_UNVERIFIED,
        NetworkErrorCode.ERROR_CODE_CONNECT_TIMEOUT,
        NetworkErrorCode.ERROR_CODE_CLASS_CAST,
        NetworkErrorCode.ERROR_CODE_NULL,
        NetworkErrorCode.ERROR_CODE_DOWNLOAD,
})
public @interface NetworkErrorCode {

    /**
     * 未知异常
     */
    int ERROR_CODE_UNKNOWN = 1000;
    /**
     * json解析失败
     */
    int ERROR_CODE_JSON_PARSE = 1001;
    /**
     * 连接失败
     */
    int ERROR_CODE_CONNECT_FAIL = 1002;
    /**
     * 证书验证失败
     */
    int ERROR_CODE_SSL_HANDSHAKE = 1005;
    /**
     * 证书路径没找到
     */
    int ERROR_CODE_CERT_PATH_INVALID = 1006;
    /**
     * 无有效的SSL证书
     */
    int ERROR_CODE_SSL_PEER_UNVERIFIED = 1007;
    /**
     * 连接超时
     */
    int ERROR_CODE_CONNECT_TIMEOUT = 1008;
    /**
     * 类型转换出错
     */
    int ERROR_CODE_CLASS_CAST = 1009;
    /**
     * 数据为空
     */
    int ERROR_CODE_NULL = -100;
    /**
     * 下载失败
     */
    int ERROR_CODE_DOWNLOAD = 1010;
}
