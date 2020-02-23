package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    //内存充足时的软引用
    @Test
    public void demo1() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);

        System.out.println(o);
        System.out.println(softReference.get());
        //输出结果：
        //java.lang.Object@1b9e1916
        //java.lang.Object@1b9e1916

        o = null;
        System.gc();

        System.out.println(o);
        System.out.println(softReference.get());
        //输出结果：
        //null
        //java.lang.Object@1b9e1916
    }

    //内存不足时的软引用
    //配置一个大对象，并调整堆内存大小
    //-Xms5m -Xmx5m -XX:+PrintGCDetails
    @Test
    public void demo2() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);

        System.out.println(o);
        System.out.println(softReference.get());
        //输出结果：
        //java.lang.Object@1b9e1916
        //java.lang.Object@1b9e1916

        o = null;

        //创建一个大对象
        try{
            byte[] bytes = new byte[30*1024*1024];
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(o);
            System.out.println(softReference.get());
        }
        //输出结果：
        //null
        //null
        //系统内存不足时，弱引用被回收
    }
}
