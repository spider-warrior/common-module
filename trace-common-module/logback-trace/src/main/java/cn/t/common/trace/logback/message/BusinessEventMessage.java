package cn.t.common.trace.logback.message;

import java.io.Serializable;
import java.util.Properties;

/**
 * business event message
 *
 * @author yj
 * @since 2021-01-26 14:41
 **/
public class BusinessEventMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String eventType;
    private String applicationName;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
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
            ", applicationName='" + applicationName + '\'' +
            ", properties=" + properties +
            '}';
    }
}
