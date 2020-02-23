package 第二季_10_多线程下的单例模式.代码;

import org.junit.Test;

public class Demo {
    @Test
    public void demo1() {
        SingletonDemo.getInstance();
        SingletonDemo.getInstance();
        SingletonDemo.getInstance();
        SingletonDemo.getInstance();
        SingletonDemo.getInstance();
        //结果：
        //只输出一遍 构造函数执行了。。。
    }

    @Test
    public void demo2() {
        for (int i = 0; i < 10; i++) {
            new Thread(SingletonDemo::getInstance, String.valueOf(i)).start();
        }
        //结果
        //可能输出一次，也可能输出多次
    }

    @Test
    public void demo3() {
        for (int i = 0; i < 10; i++) {
            new Thread(SingletonDemo::getInstance2, String.valueOf(i)).start();
        }
        //结果
        //只会输出一次
    }

    @Test
    public void demo4() {
        for (int i = 0; i < 10; i++) {
            new Thread(SingletonDemo::getInstance3, String.valueOf(i)).start();
        }
        //结果
        //只会输出一次
    }
}
