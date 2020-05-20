package cn.t.common.trace.generic;

import cn.t.util.common.SystemUtil;

/**
 * traceId生成器
 *
 * @author yj
 * @since 2020-04-15 15:48
 **/
public class TraceIdGenerator {

    //[service-name]-$pid-$mills-$tid
    private static final String TRACE_ID_PATTERN = "[%s]-%d-%d-%d";

    public static String generateTraceId(String serviceName) {
        return String.format(TRACE_ID_PATTERN, serviceName, SystemUtil.getPid(), System.currentTimeMillis(), Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        System.out.println(generateTraceId("test-service"));
    }
}
