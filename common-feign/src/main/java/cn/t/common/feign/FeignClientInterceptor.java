package cn.t.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

    private final FeignClientProperties feignClientProperties;

    @Override
    public void apply(RequestTemplate template) {
        template.header(FeignClientConstants.CLIENT_IDENTITY, feignClientProperties.getIdentity());
    }

    public FeignClientInterceptor(FeignClientProperties feignClientProperties) {
        this.feignClientProperties = feignClientProperties;
    }
}
