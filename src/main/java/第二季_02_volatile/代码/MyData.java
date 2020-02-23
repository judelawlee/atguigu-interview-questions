package 第二季_02_volatile.代码;

import java.util.concurrent.atomic.AtomicInteger;

public class MyData {
    //    int number = 0;
    //不加volatile，多线程下运行自增方法存在写值丢失的问题，所以得到的结果往往比预期值小
    volatile int number = 0;

    //不需要声明volatile，难道自己就能保证可见性？
    //不初始化的情况下，初始值为0
    AtomicInteger atomicInteger;
    //有两个方法getAndAdd()，getAndIncrement()。第二个方法其实就是调用了第一个方法，参数为1

    public void turnTo60() {
        this.number = 60;
    }

    public void autoIncrease() {
        number++;
    }
}
