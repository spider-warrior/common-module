package cn.t.common.trace.logback;

import java.util.Map;

public class MimLogContext {
    private static final ThreadLocal<Map<String, Object>> threadLogPropertiesThreadLocal = new ThreadLocal<>();
    public static void setLogProperties(Map<String, Object> properties) {
        if (properties == null) {
            return;
        }
        threadLogPropertiesThreadLocal.set(properties);
    }
    public static Map<String, Object> getLogProperties() {
        return threadLogPropertiesThreadLocal.get();
    }
    public static void removeLogProperties() {
        threadLogPropertiesThreadLocal.remove();
    }
}
