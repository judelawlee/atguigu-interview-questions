package 第二季_35_BlockingQueue.代码;

import org.junit.Test;

//生产者消费者模式
//题目：一个初始值为0的变量，两个线程交替操作，一个加1，一个减1，执行5轮
public class ProducerConsumerDemo1 {
    @Test
    public void demo() {
        SharedData1 data = new SharedData1();

        //生产者线程
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                data.increase();
            }
        }).start();

        //消费者线程
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                data.decrease();
            }
        }).start();

        //输出结果：
        //Thread-0进行生产:1
        //Thread-1进行消费:0
        //Thread-0进行生产:1
        //Thread-1进行消费:0
        //Thread-0进行生产:1
        //Thread-1进行消费:0
        //Thread-0进行生产:1
        //Thread-1进行消费:0
        //Thread-0进行生产:1
        //Thread-1进行消费:0
    }
}
