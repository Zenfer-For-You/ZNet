package com.zenfer.network.error;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertPathValidatorException;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

import retrofit2.HttpException;


/**
 * 网络通用请求异常处理
 *
 * @author Zenfer
 * @date 2019/8/12 16:26
 */
public class NetworkErrorHandler {

    public static NetwordException getException(Throwable e) {
        NetwordException ex;
        if (e instanceof HttpException) {
            ex = httpException(e);
        } else if (!(e instanceof JsonParseException) && !(e instanceof JSONException) && !(e instanceof ParseException)) {
            ex = classException(e);
        } else {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_JSON_PARSE);
            ex.setMessage("解析错误");
        }
        return ex;
    }

    private static NetwordException httpException(Throwable e) {
        NetwordException ex;
        HttpException httpException = (HttpException) e;
        ex = new NetwordException(e, httpException.code());
        switch (ex.getCode()) {
            case 302:
                ex.setMessage("网络错误");
                break;
            case 401:
                ex.setMessage("未授权的请求");
                break;
            case 403:
                ex.setMessage("禁止访问");
                break;
            case 404:
                ex.setMessage("服务器地址未找到");
                break;
            case 405:
                ex.setMessage("请求方法不允许");
                break;
            case 408:
                ex.setMessage("请求超时");
                break;
            case 417:
                ex.setMessage("接口处理失败");
                break;
            case 500:
                ex.setMessage("服务器出错");
            case 502:
                ex.setMessage("无效的请求");
                break;
            case 503:
                ex.setMessage("服务器不可用");
                break;
            case 504:
                ex.setMessage("网关响应超时");
                break;
            default:
                if (TextUtils.isEmpty(ex.getMessage())) {
                    ex.setMessage(e.getMessage());
                } else if (TextUtils.isEmpty(ex.getMessage()) && e.getLocalizedMessage() != null) {
                    ex.setMessage(e.getLocalizedMessage());
                } else if (TextUtils.isEmpty(ex.getMessage())) {
                    ex.setMessage("未知错误");
                }
                break;
        }
        return ex;
    }

    private static NetwordException classException(Throwable e) {
        NetwordException ex;
        if (e instanceof ConnectException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_CONNECT_FAIL);
            ex.setMessage("连接失败");
        } else if (e instanceof SSLHandshakeException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_SSL_HANDSHAKE);
            ex.setMessage("证书验证失败");
        } else if (e instanceof CertPathValidatorException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_CERT_PATH_INVALID);
            ex.setMessage("证书路径没找到");
        } else if (e instanceof SSLPeerUnverifiedException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_SSL_PEER_UNVERIFIED);
            ex.setMessage("无有效的SSL证书");
        } else if (e instanceof ConnectTimeoutException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_CONNECT_TIMEOUT);
            ex.setMessage("连接超时");
        } else if (e instanceof SocketTimeoutException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_CONNECT_TIMEOUT);
            ex.setMessage("连接超时");
        } else if (e instanceof ClassCastException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_CLASS_CAST);
            ex.setMessage("类型转换出错");
        } else if (e instanceof NullPointerException) {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_NULL);
            ex.setMessage("数据有空");
        } else if (e instanceof UnknownHostException) {
            ex = new NetwordException(e, 404);
            ex.setMessage("服务器地址未找到,请检查网络或Url");
        } else {
            ex = new NetwordException(e, NetworkErrorCode.ERROR_CODE_UNKNOWN);
            ex.setMessage(e.getMessage());
        }
        return ex;
    }
}
