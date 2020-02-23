package 第二季_35_BlockingQueue.代码;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    @Test
    public void Demo() {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "放入1");
                queue.put(1);
                System.out.println(Thread.currentThread().getName() + "放入2");
                queue.put(2);
                System.out.println(Thread.currentThread().getName() + "放入3");
                queue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "取出" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        //输出结果：
        //Thread-0放入1
        //Thread-1取出1
        //Thread-0放入2
        //Thread-3取出2
        //Thread-0放入3
        //Thread-2取出3

        //取出一个才能放入
    }
}
