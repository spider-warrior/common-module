package cn.t.common.trace.logback.encoder;

import ch.qos.logback.classic.pattern.*;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.EncoderBase;
import cn.t.common.trace.generic.TraceConstants;
import cn.t.common.trace.logback.ContextConstants;
import cn.t.common.trace.logback.LogConstants;
import cn.t.util.common.ArrayUtil;
import cn.t.util.common.DateUtil;
import cn.t.util.common.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppLogEncoder extends EncoderBase<ILoggingEvent> {

    private static final ThreadConverter threadConverter = new ThreadConverter();

    private static final ClassOfCallerConverter classOfCallerConverter = new ClassOfCallerConverter();
    private static final MethodOfCallerConverter methodOfCallerConverter = new MethodOfCallerConverter();
    private static final LineOfCallerConverter lineOfCallerConverter = new LineOfCallerConverter();
    private static final LevelConverter levelConverter = new LevelConverter();

    private static final String time = "time";
    private static final String traceId = "traceId";
    private static final String clientId = "clientId";
    private static final String userId = "userId";
    private static final String thread = "thread";
    private static final String level = "level";
    private static final String clazz = "class";
    private static final String hostname = "hostname";
    private static final String appName = "appName";
    private static final String method = "method";
    private static final String line = "line";
    private static final String msg = "msg";
    private static final String stackTrace = "stackTrace";

    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(time, DateUtil.convertToZonedDateTimeString((new Date(event.getTimeStamp()))));
        map.put(traceId, event.getMDCPropertyMap().get(TraceConstants.TRACE_ID_NAME));
        map.put(clientId, event.getMDCPropertyMap().get(TraceConstants.CLIENT_ID_NAME));
        map.put(userId, event.getMDCPropertyMap().get(TraceConstants.USER_ID_NAME));
        map.put(hostname, ContextConstants.IP_V4);
        map.put(appName, event.getLoggerContextVO().getPropertyMap().get(TraceConstants.TRACE_APP_NAME));
        map.put(level, levelConverter.convert(event));
        map.put(clazz, classOfCallerConverter.convert(event));
        map.put(thread, threadConverter.convert(event));
        map.put(method, methodOfCallerConverter.convert(event));
        map.put(line, lineOfCallerConverter.convert(event));
        map.put(msg, event.getFormattedMessage());
        map.put(stackTrace, buildStackTrace(event.getThrowableProxy()));
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

    private String buildStackTrace(IThrowableProxy throwableProxy) {
        if(throwableProxy == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(throwableProxy.getClassName()).append(": ").append(throwableProxy.getMessage()).append(CoreConstants.LINE_SEPARATOR);
            if(!ArrayUtil.isEmpty(throwableProxy.getStackTraceElementProxyArray())) {
                for(int i=0; i<throwableProxy.getStackTraceElementProxyArray().length; i++) {
                    builder.append(throwableProxy.getStackTraceElementProxyArray()[i]).append(CoreConstants.LINE_SEPARATOR);
                }
            }
            return builder.toString();
        }
    }
}
