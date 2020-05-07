package cn.t.common.mybatis.idgenerator;

/**
 * 生成id异常
 *
 * @author yj
 * @since 2020-05-07 12:45
 **/
public class GenerateIdException extends RuntimeException {
    public GenerateIdException(String message) {
        super(message);
    }
}
