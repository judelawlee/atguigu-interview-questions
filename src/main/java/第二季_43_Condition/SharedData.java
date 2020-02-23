package 第二季_43_Condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//视频P43
public class SharedData {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    //A线程打印2遍
    public void aPrint() {
        lock.lock();
        try {
            //判断
            while (number != 1) {
                //循环等待中
            }
            //执行
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            //唤醒下一个线程
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //B线程打印2遍
    public void bPrint() {
        lock.lock();
        try {
            //判断
            while (number != 2) {
                //循环等待中
            }
            //执行
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            //唤醒下一个线程
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //B线程打印2遍
    public void cPrint() {
        lock.lock();
        try {
            //判断
            while (number != 3) {
                //循环等待中
            }
            //执行
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
            //唤醒下一个线程
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
