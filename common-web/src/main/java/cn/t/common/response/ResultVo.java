package cn.t.common.response;

import cn.t.common.service.ErrorInfo;
import cn.t.common.service.ErrorInfoEnum;

import java.io.Serializable;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 10:22
 **/
public class ResultVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static final String SUCCESS = "200";

    public static ResultVo buildSuccess() {
        return buildSuccess(null, null);
    }

    public static ResultVo buildSuccess(Object data) {
        return buildSuccess(null, data);
    }

    public static ResultVo buildSuccess(String message, Object data) {
        return doBuild(SUCCESS, message, data);
    }

    public static ResultVo buildFail() {
        return buildFail(ErrorInfoEnum.INTERNAL_SERVER_ERROR.errorInfo);
    }

    public static ResultVo buildFail(ErrorInfo errorInfo) {
        return buildFail(errorInfo.getCode(), errorInfo.getMsg());
    }

    public static ResultVo buildFail(ErrorInfo errorInfo, Object data) {
        return buildFail(errorInfo.getCode(), errorInfo.getMsg(), data);
    }

    public static ResultVo buildFail(String errorCode, String msg) {
        return buildFail(errorCode, msg, null);
    }

    public static ResultVo buildFail(String errorCode, Object data) {
        return buildFail(errorCode, null, data);
    }

    public static ResultVo buildFail(String errorCode, String message, Object data) {
        return doBuild(errorCode, message, data);
    }

    private static ResultVo doBuild(String code, String message, Object data) {
        ResultVo vo = new ResultVo();
        vo.setCode(code);
        vo.setMessage(message);
        vo.setData(data);
        return vo;
    }
}
