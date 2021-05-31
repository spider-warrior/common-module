package cn.t.common.trace.generic.test;

import cn.t.common.trace.generic.TraceIdGenerator;
import org.junit.Test;

/**
 * @author <a href="mailto:yangjian@liby.ltd">研发部-杨建</a>
 * @version V1.0
 * @since 2020-12-16 19:19
 **/
public class TraceIdGeneratorTest {

    @Test
    public void generateTraceIdTest() {
        System.out.println(TraceIdGenerator.generateTraceId(null));
        System.out.println(TraceIdGenerator.generateTraceId("test-service"));
    }
}
