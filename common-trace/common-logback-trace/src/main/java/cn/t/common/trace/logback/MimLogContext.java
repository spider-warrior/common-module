package cn.t.common.trace.logback;

import java.util.HashMap;
import java.util.Map;

public class MimLogContext {
    private static final ThreadLocal<Map<String, Object>> threadLogPropertiesThreadLocal = ThreadLocal.withInitial(HashMap::new);
    public static Map<String, Object> threadLogPropertyMap() {
        return threadLogPropertiesThreadLocal.get();
    }
    public static void addLogProperty(String key, Object value) {
        threadLogPropertiesThreadLocal.get().put(key, value);
    }
    public static void clearLogProperties() {
        threadLogPropertiesThreadLocal.get().clear();
    }
}
