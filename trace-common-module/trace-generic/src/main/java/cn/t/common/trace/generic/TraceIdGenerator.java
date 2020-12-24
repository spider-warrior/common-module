package cn.t.common.trace.generic;

import cn.t.util.common.StringUtil;
import cn.t.util.common.SystemUtil;

/**
 * traceId生成器
 *
 * @author yj
 * @since 2020-04-15 15:48
 **/
public class TraceIdGenerator {

    private static final String IP = SystemUtil.getLocalIpV4(true);
    private static final String PID = SystemUtil.getPid();
    //[service-name]-$pid-$mills-$tid
    private static final String TRACE_ID_PATTERN = "[%s]-%s-%d-%d";
    private static final String REMOTE_TRACE_ID_PATTERN = "[%s][%s]-%s-%d-%d";

    public static String generateTraceId(String serviceName) {
        java.time.Instant.now();
        if(StringUtil.isEmpty(serviceName)) {
            serviceName = IP;
        }
        return String.format(TRACE_ID_PATTERN, serviceName, PID, SystemUtil.now(), Thread.currentThread().getId());
    }

    public static String generateTraceId(String serviceName, String remoteAddr) {
        java.time.Instant.now();
        if(StringUtil.isEmpty(serviceName)) {
            serviceName = IP;
        }
        return String.format(REMOTE_TRACE_ID_PATTERN, remoteAddr, serviceName, PID, SystemUtil.now(), Thread.currentThread().getId());
    }
}
