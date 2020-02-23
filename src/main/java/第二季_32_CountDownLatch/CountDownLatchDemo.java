package 第二季_32_CountDownLatch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

//视频P32
public class CountDownLatchDemo {
    //演示CountDownLatch类的使用
    @Test
    public void demo() {
        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "执行了");
                countDownLatch.countDown();//一个线程执行完成就将数量减1
            }).start();
        }

        try {
            countDownLatch.await();//这一步干嘛用的
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "执行了");

        //输出结果：
        //Thread-1执行了
        //Thread-0执行了
        //Thread-4执行了
        //Thread-2执行了
        //Thread-3执行了
        //main执行了
    }
}
