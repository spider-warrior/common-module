package cn.t.common.trace.logback;

import ch.qos.logback.classic.pattern.LevelConverter;
import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.pattern.ThreadConverter;
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
public class JsonLogLayout extends LayoutBase<ILoggingEvent> {

    private static final LevelConverter levelConverter = new LevelConverter();
    private static final MethodOfCallerConverter methodOfCallerConverter = new MethodOfCallerConverter();
    private static final LineOfCallerConverter lineOfCallerConverter = new LineOfCallerConverter();
    private static final ThreadConverter threadConverter = new ThreadConverter();

    private static final String ERROR_PATTERN = "{\"error\": \"%s\"}";

    private static final String traceId = "traceId";
    private static final String level = "level";
    private static final String time = "time";
    private static final String host = "host";
    private static final String appName = "appName";
    private static final String thread = "thread";
    private static final String clazz = "class";
    private static final String method = "method";
    private static final String line = "line";
    private static final String msg = "msg";

    @Override
    public String doLayout(ILoggingEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(level, levelConverter.convert(event));
        map.put(traceId, event.getMDCPropertyMap().get(TraceConstants.TRACE_ID_LOG_NAME));
        map.put(time, DateUtil.convertToDateTimeString(new Date(event.getTimeStamp())));
        map.put(host, SystemUtil.getLocalIpV4(true));
        map.put(appName, event.getLoggerContextVO().getPropertyMap().get(TraceConstants.TRACE_APP_NAME));
        map.put(thread, threadConverter.convert(event));
        map.put(clazz, event.getLoggerName());
        map.put(method, methodOfCallerConverter.convert(event));
        map.put(line, lineOfCallerConverter.convert(event));
        map.put(msg, event.getFormattedMessage());
        try {
            return JsonUtil.serialize(map) + CoreConstants.LINE_SEPARATOR;
        } catch (JsonProcessingException e) {
            return String.format(ERROR_PATTERN, map.toString()) + CoreConstants.LINE_SEPARATOR;
        }
    }
}
