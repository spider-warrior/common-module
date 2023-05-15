package cn.t.common.rpc;

import cn.t.common.service.ErrorInfo;

import java.io.Serializable;

public class RpcResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "200";
    public static final String FAIL = "500";

    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RpcResult{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            ", data=" + data +
            '}';
    }

    public static <T> RpcResult<T> buildSuccess() {
        return doBuild(SUCCESS, null, null);
    }

    public static <T> RpcResult<T> buildSuccess(T data) {
        return doBuild(SUCCESS, null, data);
    }

    public static <T> RpcResult<T> buildSystemError(String msg) {
        return buildFail(FAIL, msg);
    }

    public static <T> RpcResult<T> buildFail(String errorCode) {
        return buildFail(errorCode, null);
    }

    public static <T> RpcResult<T> buildFail(String errorCode, String message) {
        return buildFail(errorCode, message, null);
    }

    public static <T> RpcResult<T> buildFail(String errorCode, T data) {
        return buildFail(errorCode, null, data);
    }

    public static <T> RpcResult<T> buildFail(ErrorInfo errorInfo) {
        return buildFail(errorInfo, null);
    }

    public static <T> RpcResult<T> buildFail(ErrorInfo errorInfo, T data) {
        return buildFail(errorInfo.getCode(), errorInfo.getMsg(), data);
    }

    public static <T> RpcResult<T> buildFail(String errorCode, String message, T data) {
        return doBuild(errorCode, message, data);
    }

    private static <T> RpcResult<T> doBuild(String code, String message, T data) {
        RpcResult<T> result = new RpcResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
