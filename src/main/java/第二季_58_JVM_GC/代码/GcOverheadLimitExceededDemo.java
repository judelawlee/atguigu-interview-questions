package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GcOverheadLimitExceededDemo {
    //模拟OutOfMemoryError: GC overhead limit exceeded错误
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
    @Test
    public void demo() {
        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }

        //抛出错误：
        //java.lang.OutOfMemoryError: GC overhead limit exceeded
    }
}
