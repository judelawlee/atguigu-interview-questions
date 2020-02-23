package 第二季_25_锁.代码;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Phone implements Runnable {
    public synchronized void sendSmg() {
        System.out.println(Thread.currentThread().getName() + "-msg");
        sendEmail();
    }

    //内层方法也必须加synchronized吗
    //不加的话应该就是把内层方法的代码放到外层方法里执行，也不存在重入不重入的问题
    //只有内层外层都加了synchronized，才谈得上重入
    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "-email");
    }


    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        method1();
    }

    private void method1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "-method1()");
            method2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void method2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "-method2()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //这个方法与method1()对应
    //但是多加了一层加锁解锁
    //这样并不会产生任何错误，就是完全没有必要
    //但如果只加锁，不解锁，后面的线程就无法执行了
    private void method3() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "-method1()");
            method2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
}
