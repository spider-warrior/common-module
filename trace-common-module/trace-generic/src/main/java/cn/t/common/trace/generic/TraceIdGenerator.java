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

    //[service-name]-$pid-$mills-$tid
    private static final String TRACE_ID_PATTERN = "[%s]-%s-%d-%d";

    public static String generateTraceId(String serviceName) {
        if(StringUtil.isEmpty(serviceName)) {
            serviceName = SystemUtil.getLocalIpV4(true);
        }
        return String.format(TRACE_ID_PATTERN, serviceName, SystemUtil.getPid(), System.currentTimeMillis(), Thread.currentThread().getId());
    }
}
