package cn.t.common.feign;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;

public class BeanUtil {
    public static ResponseEntityDecoder responseEntityDecoder(ObjectFactory<HttpMessageConverters> objectFactory) {
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
    public static SpringEncoder springEncoder(ObjectFactory<HttpMessageConverters> objectFactory) {
        return new SpringEncoder(objectFactory);
    }
}
