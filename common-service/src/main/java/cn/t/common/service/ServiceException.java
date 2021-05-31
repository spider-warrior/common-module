package cn.t.common.service;

/**
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
