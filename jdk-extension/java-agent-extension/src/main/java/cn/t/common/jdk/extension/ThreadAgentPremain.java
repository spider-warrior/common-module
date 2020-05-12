package cn.t.common.jdk.extension;

import java.lang.instrument.Instrumentation;

/**
 * @author yj
 * @since 2020-05-12 09:35
 **/
public class ThreadAgentPremain {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain runs....");
        inst.addTransformer(new ThreadTransformer());
    }
}
