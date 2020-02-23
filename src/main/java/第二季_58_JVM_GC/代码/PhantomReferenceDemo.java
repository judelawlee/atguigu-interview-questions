package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    @Test
    public void demo() {
        Object o = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        //注意虚引用的两个参数
        PhantomReference<Object> phantomReference = new PhantomReference<>(o, referenceQueue);

        System.out.println(o);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        //输出结果：
        //java.lang.Object@1b9e1916
        //null
        //null

        o =null;
        System.gc();

        System.out.println(o);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        //输出结果：
        //null
        //null
        //java.lang.ref.PhantomReference@ba8a1dc
        //可以看到被回收以后放入了引用队列
    }
}
