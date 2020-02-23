package 第二季_45_Callable;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//视频P45
public class CallableDemo {
    @Test
    public void demo1() {
        //先构建一个FutureTask对象，将我的Callable类作为参数传进去
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());

        //将futureTask作为参数传给Thread对象
        Thread thread = new Thread(futureTask);
        thread.start();

        //从futureTask获取call()方法的返回值
        //futureTask.get()如果没有完成运算，会产生阻塞（阻塞主线程吗）
        //所以最好放在运算的最后
        try {
            System.out.println("返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //输出结果：
        //call()执行了
        //返回值：call
    }

    @Test
    public void demo2() {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());

        //启用两个线程来执行call()
        new Thread(futureTask).start();
        new Thread(futureTask).start();

        //下面这一段不写的时候，没有输出结果
        //推测原因是因为程序还在运行，但是打印的功能已经停止了
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //输出结果：
        //call()执行了

        //说明用两个线程的时候，call()方法也只执行一次
        //如果像执行多次call()，就需要多创建FutureTask对象
    }

    @Test
    public void demo3() {
        //创建两个FutureTask对象
        FutureTask<String> futureTask1 = new FutureTask<>(new MyCallable());
        FutureTask<String> futureTask2 = new FutureTask<>(new MyCallable());

        new Thread(futureTask1).start();
        new Thread(futureTask2).start();

        //下面这一段不写的时候，没有输出结果
        //推测原因是因为程序还在运行，但是打印的功能已经停止了
        try {
            futureTask1.get();
            futureTask2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //输出结果：
        //call()执行了
        //call()执行了
    }
}
