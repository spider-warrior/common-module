/**
 * @author yj
 * @since 2020-05-09 19:40
 **/
public class ThreadTest {
    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());
        InheritableThreadLocal<Object> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set("999999");
        ThreadLocal<Object> threadLocal = new ThreadLocal<>();
        threadLocal.set("123456");
        Thread thread = new Thread(() -> {
            System.out.println(inheritableThreadLocal.get());
            System.out.println(threadLocal.get());
            Thread innerThread = new Thread(() -> {
                System.out.println(Thread.currentThread());
            });
            innerThread.start();
        });
        thread.start();
    }

    private static class MySecurityManager extends SecurityManager {
    }
}
