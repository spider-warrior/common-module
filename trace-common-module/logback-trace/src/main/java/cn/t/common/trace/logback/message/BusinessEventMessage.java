package cn.t.common.trace.logback.message;

import java.util.Properties;

/**
 * business event messge
 *
 * @author yj
 * @since 2021-01-26 14:41
 **/
public class BusinessEventMessage {
    private String userId;
    private String eventType;
    private Properties properties;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BusinessEventMessage{" +
            "userId='" + userId + '\'' +
            ", eventType='" + eventType + '\'' +
            ", properties=" + properties +
            '}';
    }
}
