package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    @Test
    public void demo1() {
        Map<Integer, String> map = new HashMap<>();
        Integer i = new Integer(1);
        map.put(i, "123");
        System.out.println(map);
        //输出结果：
        //{1=123}
        //将键置空
        i = null;
        System.out.println(map);
        //输出结果：
        //{1=123}

        //注意，这里把i置空不会影响到map里面的键
    }

    @Test
    public void demo2() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer i = new Integer(1);
        map.put(i, "123");
        System.out.println(map);
        //输出结果：
        //{1=123}
        //将键置空
        i = null;
        System.out.println(map);
        //输出结果：
        //{1=123}

        System.gc();
        System.out.println(map);
        //输出结果：
        //{}

        //只要gc，就会回收map里的内容
    }
}
