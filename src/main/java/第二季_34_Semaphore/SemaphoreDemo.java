package 第二季_34_Semaphore;

import org.junit.Test;

import java.util.concurrent.Semaphore;

//视频P34
//信号量的作用：一个是多个共享资源的互斥使用，一个是并发线程数的控制
public class SemaphoreDemo {
    //模拟抢车位
    @Test
    public void demo() {
        int count = 3;
        Semaphore semaphore = new Semaphore(count);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();//模拟抢到车位
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//模拟离开车位
                }
            }, "线程" + i).start();
        }
    }
}
