package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {
    @Test
    public void demo() {
        Object o = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o);

        System.out.println(o);
        System.out.println(weakReference.get());
        //输出结果：
        //java.lang.Object@1b9e1916
        //java.lang.Object@1b9e1916

        o = null;
        System.gc();

        System.out.println(o);
        System.out.println(weakReference.get());
        //输出结果：
        //null
        //null
    }
}
