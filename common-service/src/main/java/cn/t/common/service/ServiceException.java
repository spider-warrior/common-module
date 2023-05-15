package cn.t.common.service;

/**
 * ServiceException
 *
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 10:10
 **/
public class ServiceException extends RuntimeException {

    /**
     * code
     */
    private final String code;
    /**
     * 信息
     */
    private final String message;
    /**
     * 数据
     */
    private final Object data;

    public ServiceException(ErrorInfo errorInfo) {
        this(errorInfo.getCode(), errorInfo.getMsg());
    }

    public ServiceException(ErrorInfo errorInfo, Object... args) {
        this(errorInfo.getCode(), String.format(errorInfo.getMsg(), args));
    }

    public ServiceException(ErrorInfo errorInfo, Object data) {
        this(errorInfo.getCode(), errorInfo.getMsg(), data);
    }

    public ServiceException(String code, String message) {
        this(code, message, null);
    }

    public ServiceException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ServiceException(Throwable e) {
        this(e, ErrorInfoEnum.INTERNAL_SERVER_ERROR.errorInfo);
    }

    public ServiceException(Throwable e, ErrorInfo errorInfo) {
        this(e, errorInfo.getCode(), errorInfo.getMsg());
    }

    public ServiceException(Throwable e, String code, String message) {
        this(e, code, message, null);
    }

    public ServiceException(Throwable cause, String code, String message, Object data) {
        super(cause);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            ", data=" + data +
            "} " + super.toString();
    }
}
