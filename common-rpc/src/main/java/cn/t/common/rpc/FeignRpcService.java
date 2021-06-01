package cn.t.common.rpc;

import cn.t.common.service.ErrorInfoEnum;
import cn.t.common.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.TreeMap;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-22 19:26
 **/
public class FeignRpcService {

    private final Logger logger = LoggerFactory.getLogger(FeignRpcService.class);

    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RpcResult<Object> methodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        TreeMap<String, String> fieldErrorMap = buildErrorField(bindingResult);
        RpcResult<Object> result;
        if(fieldErrorMap.size() > 0 && fieldErrorMap.firstEntry() != null) {
            result = RpcResult.buildFail(ErrorInfoEnum.BAD_PARAM.errorInfo.getCode(), fieldErrorMap.firstEntry().getValue(), fieldErrorMap);
        } else {
            result = RpcResult.buildFail(ErrorInfoEnum.BAD_PARAM.errorInfo);
        }
        logger.error("cat a MethodArgumentNotValidException, {}", result);
        return result;
    }

    // 400
    @ExceptionHandler(BindException.class)
    public RpcResult<Object> methodArgumentNotValid(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        RpcResult<Object> result = RpcResult.buildFail(ErrorInfoEnum.BAD_PARAM.errorInfo, buildErrorField(bindingResult));
        logger.error("cat a BindException, {}", result);
        return result;
    }

    private TreeMap<String, String> buildErrorField(BindingResult bindingResult) {
        List<ObjectError> objectErrorList =  bindingResult.getAllErrors();
        TreeMap<String, String> errorFieldMap = new TreeMap<>();
        objectErrorList.forEach(error -> {
            if(error instanceof FieldError) {
                errorFieldMap.put(((FieldError)error).getField(), error.getDefaultMessage());
            }
        });
        return errorFieldMap;
    }

    // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public RpcResult<Object> noHandlerFound(NoHandlerFoundException e) {
        logger.error("cat a NoHandlerFoundException", e);
        return RpcResult.buildFail(ErrorInfoEnum.SOURCE_NOT_FOUND.errorInfo);
    }

    // 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RpcResult<Object> messageNotReadable(HttpMessageNotReadableException e) {
        logger.error("cat a HttpMessageNotReadableException", e);
        return RpcResult.buildFail(ErrorInfoEnum.BAD_PARAM.errorInfo);
    }

    // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RpcResult<Object> methodNotSupport(HttpRequestMethodNotSupportedException e) {
        logger.error("cat a HttpRequestMethodNotSupportedException", e);
        return RpcResult.buildFail(ErrorInfoEnum.METHOD_NOT_SUPPORT.errorInfo);
    }

    // service exception
    @ExceptionHandler(ServiceException.class)
    public RpcResult<Object> exception(ServiceException e) {
        if(StringUtils.isEmpty(e.getCode())) {
            logger.warn("业务异常, errorCode is null", e);
            return RpcResult.buildFail(ErrorInfoEnum.SERVER_INTERNAL_ERROR.errorInfo);
        } else {
            logger.warn("业务异常, code: {}, msg: {}, data: {}", e.getCode(), e.getMessage(), e.getData());
            return RpcResult.buildFail(e.getCode(), e.getMessage(), e.getData());
        }
    }

    // 500
    @ExceptionHandler(Throwable.class)
    public RpcResult<Object> exception(Throwable t) {
        logger.error("catch a exception", t);
        return RpcResult.buildFail(ErrorInfoEnum.SERVER_INTERNAL_ERROR.errorInfo);
    }
}
