package 第二季_25_锁.代码;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//手写一个自旋锁
public class SpinLockDemo {
    //刚初始化的时候，atomicReference对象里面的Thread对象是null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public void lock() {
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + "线程进入");
        //atomicReference.compareAndSet(null, thread)方法
        //如果，atomicReference对象里面的Thread对象是null，就会赋值当前线程对象，并返回true
        //取反后为false，结束循环
        //如果，atomicReference对象里面的Thread对象不是null，说明有其他线程占用着
        //就返回false，取反后得到true，继续循环
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    //解锁
    public void unlock() {
        Thread thread = Thread.currentThread();
        //这一步模拟出了一个释放锁的操作
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "释放锁");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            //停3s模拟一个耗时操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        },"T1").start();

        //停1s，保证T2在T1之后启动
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unlock();
        },"T2").start();

        //输出结果：
        //T1线程进入
        //T2线程进入
        //T1释放锁
        //T2释放锁
    }
}
