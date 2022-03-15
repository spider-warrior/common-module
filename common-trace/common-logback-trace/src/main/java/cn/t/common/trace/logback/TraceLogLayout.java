package cn.t.common.trace.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import cn.t.common.trace.generic.TraceConstants;
import cn.t.util.common.DateUtil;
import cn.t.util.common.JsonUtil;
import cn.t.util.common.SystemUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义json格式输出
 *
 * @author yj
 * @since 2020-04-15 19:51
 **/
public class TraceLogLayout extends LayoutBase<ILoggingEvent> {

    private static final String CURRENT_IP = SystemUtil.getLocalIpV4(true);

    private static final String time = "time";
    private static final String traceId = "traceId";
    private static final String pSpanId = "pSpanId";
    private static final String spanId = "spanId";
    private static final String hostname = "hostname";
    private static final String appName = "appName";
    private static final String clazz = "class";
    private static final String method = "method";
    private static final String startTime = "startTime";
    private static final String endTime = "endTime";

    @Override
    public String doLayout(ILoggingEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(time, DateUtil.convertToZonedDateTimeString((new Date(event.getTimeStamp()))));
        map.put(traceId, event.getMDCPropertyMap().get(TraceConstants.TRACE_ID_NAME));
        map.put(pSpanId, event.getMDCPropertyMap().get(TraceConstants.P_SPAN_ID_NAME));
        map.put(spanId, event.getMDCPropertyMap().get(TraceConstants.SPAN_ID_NAME));
        map.put(hostname, CURRENT_IP);
        map.put(appName, event.getLoggerContextVO().getPropertyMap().get(TraceConstants.TRACE_APP_NAME));
        map.put(clazz, event.getMDCPropertyMap().get(TraceConstants.TRACE_CLASS_NAME));
        map.put(method, event.getMDCPropertyMap().get(TraceConstants.TRACE_METHOD_NAME));
        map.put(startTime, event.getMDCPropertyMap().get(TraceConstants.TRACE_START_TIME_NAME));
        map.put(endTime, event.getMDCPropertyMap().get(TraceConstants.TRACE_END_TIME_NAME));
        try {
            return JsonUtil.serialize(map) + CoreConstants.LINE_SEPARATOR;
        } catch (JsonProcessingException e) {
            return String.format(LogConstants.ERROR_MESSAGE_PATTERN, map.toString()) + CoreConstants.LINE_SEPARATOR;
        }
    }
}
