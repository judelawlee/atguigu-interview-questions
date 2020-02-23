package 第二季_16_ABA问题.代码;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {
    //演示AtomicReference类的使用
    @Test
    public void demo1() {
        User zhangSan = new User("张三", 10);
        User liSi = new User("李四", 15);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zhangSan);//这一步别忘了

        //参数1：预期值
        //参数2：期望修改成的值
        System.out.println(atomicReference.compareAndSet(zhangSan, liSi));
        System.out.println(atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(zhangSan, liSi));
        System.out.println(atomicReference.get().toString());

        //输出结果:
        //true
        //User{userName='李四', age=15}
        //false
        //User{userName='李四', age=15}
    }

    //演示ABA问题
    @Test
    public void demo2() {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100, 101));
            System.out.println(atomicReference.compareAndSet(101, 100));
        }).start();

        new Thread(() -> {
            System.out.println(1);
            //学到了新的暂停线程的方法
            try {
                System.out.println(2);
                //这个类在并发包下
                //停1s保证第一个线程执行完成
                Thread.sleep(3000);
                System.out.println(3);
            } catch (InterruptedException e) {
                System.out.println(4);
                e.printStackTrace();
            }
            System.out.println(5);
            System.out.println(atomicReference.compareAndSet(100, 200));
            System.out.println(atomicReference.get());
        }).start();

        //为什么什么也没有打印出来!!!
        //只打印出了1，2
    }

    //使用AtomicStampedReference类解决ABA问题
    @Test
    public void demo3() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程1：" + stamp);

            //停1s
            try {
                //这个类在并发包下
                //停1s保证第一个线程执行完成
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            atomicStampedReference.compareAndSet(100,
                    101,
                    atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp() + 1);
            System.out.println("线程1：" + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(101,
                    100,
                    atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp() + 1);
            System.out.println("线程1：" + atomicStampedReference.getStamp());
        }).start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程2：" + stamp);

            try {
                //这个类在并发包下
                //停1s保证第一个线程执行完成
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //这里的时间戳一定要用一开始获取到的stamp
            atomicStampedReference.compareAndSet(101,
                    100,
                    stamp,
                    stamp + 1);
            System.out.println("线程2：" + atomicStampedReference.getStamp());
        }).start();

        //打印也有问题
    }
}
