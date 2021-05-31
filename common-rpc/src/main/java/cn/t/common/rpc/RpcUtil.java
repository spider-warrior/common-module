package cn.t.common.rpc;

import cn.t.common.service.ErrorInfoEnum;
import cn.t.common.service.ServiceException;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2021-03-17 11:48
 **/
public class RpcUtil {
    public static void checkResult(RpcResult<?> result) {
        if(!Objects.equals(RpcResult.SUCCESS, result.getCode())) {
            //rpc system error
            if(Objects.equals(RpcResult.FAIL, result.getCode()) || StringUtils.isEmpty(result.getCode())) {
                throw new ServiceException(ErrorInfoEnum.SERVER_INTERNAL_ERROR.errorInfo);
            } else {
                //rpc service error
                throw new ServiceException(result.getCode(), result.getMessage(), result.getData());
            }
        }
    }

    public static<T> T extractResult(RpcResult<T> result) {
        checkResult(result);
        return result.getData();
    }
}
