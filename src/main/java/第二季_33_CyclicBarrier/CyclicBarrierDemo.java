package 第二季_33_CyclicBarrier;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//视频P33
public class CyclicBarrierDemo {
    @Test
    public void demo() {
        int count = 5;//线程数

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
            System.out.println("所有前置任务已完成");
        });

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"执行完成");

                //下面这一句是重点
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "线程" + i).start();
        }

        //输出结果：
        //线程1执行完成
        //线程2执行完成
        //线程4执行完成
        //线程0执行完成
        //线程3执行完成
        //所有前置任务已完成
    }
}
