package cn.t.common.trace.logback.encoder;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EncoderBase;
import cn.t.common.trace.generic.TraceConstants;
import cn.t.common.trace.logback.ContextConstants;
import cn.t.common.trace.logback.LogConstants;
import cn.t.util.common.DateUtil;
import cn.t.util.common.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TraceLogEncoder extends EncoderBase<ILoggingEvent> {

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
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(time, DateUtil.convertToZonedDateTimeString((new Date(event.getTimeStamp()))));
        map.put(traceId, event.getMDCPropertyMap().get(TraceConstants.TRACE_ID_NAME));
        map.put(pSpanId, event.getMDCPropertyMap().get(TraceConstants.P_SPAN_ID_NAME));
        map.put(spanId, event.getMDCPropertyMap().get(TraceConstants.SPAN_ID_NAME));
        map.put(hostname, ContextConstants.IP_V4);
        map.put(appName, event.getLoggerContextVO().getPropertyMap().get(TraceConstants.TRACE_APP_NAME));
        map.put(clazz, event.getMDCPropertyMap().get(TraceConstants.TRACE_CLASS_NAME));
        map.put(method, event.getMDCPropertyMap().get(TraceConstants.TRACE_METHOD_NAME));
        map.put(startTime, event.getMDCPropertyMap().get(TraceConstants.TRACE_START_TIME_NAME));
        map.put(endTime, event.getMDCPropertyMap().get(TraceConstants.TRACE_END_TIME_NAME));
        try {
            return (JsonUtil.serialize(map) + CoreConstants.LINE_SEPARATOR).getBytes();
        } catch (JsonProcessingException e) {
            return (String.format(LogConstants.ERROR_MESSAGE_PATTERN, map) + CoreConstants.LINE_SEPARATOR).getBytes();
        }
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }
}
