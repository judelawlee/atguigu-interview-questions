package 第二季_35_BlockingQueue.代码;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

//生产者消费者模式 阻塞队列版
//这个有点费解
public class ProducerConsumerDemo2 {
    @Test
    public void demo() {
        SharedData2 data = new SharedData2(new ArrayBlockingQueue<>(3));

        //生产线程
        new Thread(() -> {
            data.produce();
        }).start();

        //消费线程
        new Thread(() -> {
            data.consume();
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.stop();
    }

}
