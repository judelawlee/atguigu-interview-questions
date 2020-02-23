package 第二季_25_锁.代码;

import org.junit.Test;

public class ReentrantLockDemo {
    //演示synchronized的可重入
    @Test
    public void demo1() {
        Phone phone = new Phone();

        new Thread(phone::sendSmg).start();

        new Thread(phone::sendSmg).start();

        //输出结果：
        //Thread-0-msg
        //Thread-0-email
        //Thread-1-msg
        //Thread-1-email
    }

    //演示ReentrantLock的可重入
    public void demo2() {
        Phone phone = new Phone();

        Thread thread1 = new Thread(phone,"T1");
        Thread thread2 = new Thread(phone,"T2");
        thread1.start();
        thread2.start();

        //打断点执行的时候，输出
        //T2-method1()
        //T2-method2()
        //T1-method1()
        //T1-method2()
    }

    public static void main(String[] args) {
        new ReentrantLockDemo().demo2();
    }

}
