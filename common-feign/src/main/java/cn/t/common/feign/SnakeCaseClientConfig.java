package cn.t.common.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class SnakeCaseClientConfig {

    protected static final ObjectMapper objectMapper = ObjectMapperUtil.buildObjectMapper(PropertyNamingStrategy.SNAKE_CASE);
    protected static final HttpMessageConverter<Object> jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
    protected static final HttpMessageConverters messageConverters = new HttpMessageConverters(jacksonConverter);
    protected static final ObjectFactory<HttpMessageConverters> objectFactory = () -> messageConverters;

    @Bean
    Decoder snakeCaseFeignDecoder() {
        return BeanUtil.responseEntityDecoder(objectFactory);
    }

    @Bean
    Encoder snakeCaseFeignEncoder() {
        return BeanUtil.springEncoder(objectFactory);
    }

}
