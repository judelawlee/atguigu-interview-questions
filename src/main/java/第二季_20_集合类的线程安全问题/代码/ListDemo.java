package 第二季_20_集合类的线程安全问题.代码;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListDemo {

    //演示集合类的并发安全问题
    @Test
    public void demo1() {
        List<String> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }

        //出现异常：Exception in thread "Thread-0" java.util.ConcurrentModificationException
    }

    //使用Vector类
    //Vector类继承自AbstractList类，AbstractList类实现了List接口
    @Test
    public void demo2() {
        List<String> list = new Vector<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }

    //使用Collections.synchronizedList()方法，参数为一个list
    @Test
    public void demo3() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }

    //使用CopyOnWriteArrayList类
    @Test
    public void demo4() {
        List<String> list = new CopyOnWriteArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(String.valueOf(random.nextInt()));
                System.out.println(list);
            }).start();
        }
    }
}
