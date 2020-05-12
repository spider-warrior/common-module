package cn.t.common.jdk.extension;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtPrimitiveType;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author yj
 * @since 2020-05-11 21:12
 **/
public class ThreadTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if("java.lang.Thread".equals(className)) {
            try {
                CtClass ctClass = ClassPool.getDefault().get(className);
                CtClass[] paraTypes = {
                    ClassPool.getDefault().get("java.lang.ThreadGroup"),
                    ClassPool.getDefault().get("java.lang.Runnable"),
                    ClassPool.getDefault().get("java.lang.String"),
                    CtPrimitiveType.longType,
                    ClassPool.getDefault().get("java.security.AccessControlContext"),
                    CtPrimitiveType.booleanType
                };
                CtMethod initMethod = ctClass.getDeclaredMethod("init", paraTypes);
                initMethod.insertBefore("System.out.println(\"起飞之前准备降落伞\");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
