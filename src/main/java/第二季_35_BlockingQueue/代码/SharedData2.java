package 第二季_35_BlockingQueue.代码;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedData2 {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> queue = null;

    public SharedData2(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    //生产
    public void produce() {
        String data = null;
        boolean returnValue;
        while (flag) {
            data = String.valueOf(atomicInteger.incrementAndGet());
            try {
                returnValue = queue.offer(data,1500, TimeUnit.MILLISECONDS);
                if (returnValue) {
                    //插入队列成功
                    System.out.println(Thread.currentThread().getName() + "插入队列成功" + data);
                } else {
                    System.out.println(Thread.currentThread().getName() + "插入队列失败" + data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //执行到这一行说明while循环停止，也就是结束生产
        System.out.println(Thread.currentThread().getName() + "停止生产");
    }

    //消费
    public void consume() {
        String result = null;
        while (flag) {
            try {
                result = queue.poll(1L, TimeUnit.SECONDS);//这里设置的时限是因为有时候队列为空，但还在生产
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果1s内队列还是空的，就结束消费
            if (result == null) {
                System.out.println("没有取到，结束消费");
            }
            System.out.println(Thread.currentThread().getName() + "移出队列成功" + result);
        }
    }

    //停止
    public void stop() {
        flag = false;
        System.out.println("下达停止生产命令");
    }
}
