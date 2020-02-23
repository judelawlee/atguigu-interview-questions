package 第二季_20_集合类的线程安全问题.代码;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapDemo {
    //演示HashMap类的并发问题
    @Test
    public void demo1() {
        Map<String, Integer> map = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), random.nextInt());
                System.out.println(map);
            }).start();
        }

        //报错：Exception in thread "Thread-0" Exception in thread "Thread-1" java.util.ConcurrentModificationException
    }
    //解决方法：
    //使用ConcurrentHashMap类
    //使用Collections.synchronizedMap()方法
}
