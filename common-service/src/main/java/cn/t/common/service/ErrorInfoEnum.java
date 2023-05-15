package cn.t.common.service;

public enum ErrorInfoEnum {

    BAD_PARAM(new ErrorInfo("400", "参数异常")),
    NOT_LOGIN(new ErrorInfo("401", "未登录")),
    NOT_PERMIT(new ErrorInfo("403", "无操作权限")),
    SOURCE_NOT_FOUND(new ErrorInfo("404", "资源不存在")),
    METHOD_NOT_SUPPORT(new ErrorInfo("405", "请求方法不支持")),
    MEDIA_TYPE_NOT_SUPPORT(new ErrorInfo("415", "请求媒体类型不支持")),
    TOO_MANY_REQUESTS(new ErrorInfo("429", "操作过于频繁")),
    INTERNAL_SERVER_ERROR(new ErrorInfo("500", "服务器内部异常")),
    SERVICE_UNAVAILABLE(new ErrorInfo("503", "服务器繁忙"));

    public final ErrorInfo errorInfo;

    ErrorInfoEnum(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public static ErrorInfoEnum getErrorInfoEnum(String code) {
        for (ErrorInfoEnum infoEnum : values()) {
            if (infoEnum.errorInfo.getCode().equals(code)) {
                return infoEnum;
            }
        }
        return null;
    }
}
