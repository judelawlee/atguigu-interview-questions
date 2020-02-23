package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.lang.reflect.Method;

public class MetaspaceDemo {
    //模拟OutOfMemoryError: Metaspace
    //-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
    @Test
    public void demo() {
        //视频里使用了Enhancer类，但我的JDK里面并没有这个类，难道是JDK版本问题
        //MethodInterceptor类也没有
        //百度了一下，Enhancer是cglib里的类
//        while(true){
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(OOM.class);
//            enhancer.setUserCache(false);
//            enhancer.setCallback(new MethodInterceptor({
//                @Override
//                public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable{
//                    return methodProxy.invokeSuper(o,args);
//                }
//            }));
//            enhancer.create();
//        }
    }

    //写一个静态内部类
    class OOM {

    }
}
