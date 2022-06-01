package cn.t.common.trace.logback.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import cn.t.common.trace.generic.TraceConstants;
import cn.t.common.trace.logback.LogConstants;
import cn.t.common.trace.logback.message.BusinessEventMessage;
import cn.t.util.common.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务事件
 *
 * @author yj
 * @since 2021-01-26 14:04
 **/
@Deprecated
public class BusinessEventLogLayout extends LayoutBase<ILoggingEvent> {

    private static final String CURRENT_IP = SystemUtil.getLocalIpV4(true);

    private static final String time = "time";
    private static final String traceId = "traceId";
    private static final String clientId = "clientId";
    private static final String userId = "userId";
    private static final String hostname = "hostname";
    private static final String appName = "appName";
    private static final String eventType = "eventType";
    private static final String properties = "properties";

    @Override
    public String doLayout(ILoggingEvent event) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(time, DateUtil.convertToZonedDateTimeString((new Date(event.getTimeStamp()))));
        map.put(traceId, event.getMDCPropertyMap().get(TraceConstants.TRACE_ID_NAME));
        map.put(clientId, event.getMDCPropertyMap().get(TraceConstants.CLIENT_ID_NAME));
        map.put(userId, event.getMDCPropertyMap().get(TraceConstants.USER_ID_NAME));
        map.put(hostname, CURRENT_IP);
        map.put(appName, event.getLoggerContextVO().getPropertyMap().get(TraceConstants.TRACE_APP_NAME));
        Object[] paramArray = event.getArgumentArray();
        if(!ArrayUtil.isEmpty(paramArray) && paramArray[0] instanceof BusinessEventMessage) {
            BusinessEventMessage message = (BusinessEventMessage)paramArray[0];
            if(!StringUtil.isEmpty(message.getUserId())) {
                map.put(userId, message.getUserId());
            }
            map.put(eventType, message.getEventType());
            map.put(properties, message.getProperties());
        } else {
            map.put(userId, "");
            map.put(eventType, "");
            map.put(properties, "{}");
        }
        try {
            return JsonUtil.serialize(map) + CoreConstants.LINE_SEPARATOR;
        } catch (JsonProcessingException e) {
            return String.format(LogConstants.ERROR_MESSAGE_PATTERN, map.toString()) + CoreConstants.LINE_SEPARATOR;
        }
    }
}
