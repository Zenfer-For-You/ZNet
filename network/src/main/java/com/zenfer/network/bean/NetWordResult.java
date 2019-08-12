package com.zenfer.network.bean;

/**
 * 服务器统一返回数据模型
 *
 * @author Zenfer
 * @date 2019/6/11 10:34
 */
public class NetWordResult {

    /**
     * 状态码
     */
    private int status_code;
    /**
     * 信息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NetWordResult{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
