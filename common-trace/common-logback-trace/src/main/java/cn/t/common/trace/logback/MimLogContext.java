package cn.t.common.trace.logback;

import java.util.Map;

public class MimLogContext {
    private static final ThreadLocal<Map<String, Object>> threadLogPropertiesThreadLocal = new ThreadLocal<>();
    public static void setThreadLogProperties(Map<String, Object> properties) {
        if (properties == null) {
            return;
        }
        threadLogPropertiesThreadLocal.set(properties);
    }
    public static Map<String, Object> getThreadLogProperties() {
        return threadLogPropertiesThreadLocal.get();
    }
    public static void removeEmployeeInfo() {
        threadLogPropertiesThreadLocal.remove();
    }
}
