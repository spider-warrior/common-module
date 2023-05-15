package cn.t.common.rpc;

import cn.t.common.service.ErrorInfoEnum;
import cn.t.common.service.ServiceException;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * RpcUtil
 *
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 11:48
 **/
public class RpcUtil {
    private static final DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();

    public static void checkResult(RpcResult<?> result) {
        checkResult(result, defaultErrorHandler);
    }

    public static void checkResult(RpcResult<?> result, ErrorHandler errorHandler) {
        if(!isResultSuccess(result)) {
            errorHandler.handle(result.getCode(), result.getMessage());
        }
    }

    public static boolean isResultSuccess(RpcResult<?> result) {
        return Objects.equals(RpcResult.SUCCESS, result.getCode());
    }

    public static<T> T extractResult(RpcResult<T> result) {
        return extractResult(result, null, defaultErrorHandler);
    }

    public static<T> T extractResult(RpcResult<T> result, ErrorHandler errorHandler) {
        return extractResult(result, null, errorHandler);
    }

    public static<T> T extractResult(RpcResult<T> result, SuccessHandler<T> successHandler) {
        return extractResult(result, successHandler, null);
    }

    public static<T> T extractResult(RpcResult<T> result, SuccessHandler<T> successHandler, ErrorHandler handler) {
        checkResult(result, handler);
        T t = result.getData();
        if(successHandler != null) {
            t = successHandler.handle(t);
        }
        return t;
    }

    @FunctionalInterface
    public interface ErrorHandler {
        void handle(String code, String message);
    }

    public static class DefaultErrorHandler implements ErrorHandler {
        @Override
        public void handle(String code, String message) {
            //rpc system error
            if(Objects.equals(RpcResult.FAIL, code) || !StringUtils.hasText(code)) {
                throw new ServiceException(ErrorInfoEnum.INTERNAL_SERVER_ERROR.errorInfo);
            } else {
                //rpc service error
                throw new ServiceException(code, message);
            }
        }
    }
    @FunctionalInterface
    public interface SuccessHandler<T> {
        T handle(T result);
    }
}
