package 第二季_35_BlockingQueue.代码;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedData1 {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();//这个条件是重点

    //生产
    public void increase() {
        lock.lock();
        try {
            while (number != 0) {//一定要用while，用if存在虚假唤醒的问题
                //等待，不能生产
                condition.await();
            }
            //模拟生产过程
            number++;
            System.out.println(Thread.currentThread().getName() + "进行生产:" + number);
            //生产后通知唤醒
            condition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    //消费
    public void decrease() {
        lock.lock();
        try {
            while (number == 0) {
                //等待，不能生产
                condition.await();
            }
            //模拟消费过程
            number--;
            System.out.println(Thread.currentThread().getName() + "进行消费:" + number);
            //消费后通知唤醒
            condition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}
