package cn.t.common.feign;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.feign.client")
public class FeignClientProperties {
    private String identity;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "FeignClientProperties{" +
                "identity='" + identity + '\'' +
                '}';
    }
}
