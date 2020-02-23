package 第二季_46_线程池;

import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runner.JUnitCore;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    //创建固定线程数的线程池
    @Test
    public void demo1() {
        //为什么对象接收为ExecutorService接口，而不是Executor接口
        //创建固定线程数的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-3
        //pool-1-thread-2
        //pool-1-thread-3
        //pool-1-thread-2
        //pool-1-thread-1
        //pool-1-thread-3
        //pool-1-thread-4
        //pool-1-thread-2
        //pool-1-thread-5
    }

    //创建只有一个线程的线程池
    @Test
    public void demo2() {
        //创建只有一个线程的线程池
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
    }

    //创建根据需要处理的任务创建线程的线程池
    @Test
    public void demo3() {
        //创建根据需要处理的任务创建线程的线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //有时候创建了10个线程，有时候创建了9个线程
        //根据实际情况有可能会只创建一个线程
    }

    //自定义线程池
    //new ThreadPoolExecutor.AbortPolicy()
    @Test
    public void demo4() {
        /**
         * new LinkedBlockingDeque<Runnable>(3) 表示创建了一个最大为3的链表阻塞队列，注意泛型是Runnable
         * Executors.defaultThreadFactory()是默认的线程工厂
         * new ThreadPoolExecutor.AbortPolicy()使用了内置的AbortPolicy，这是ThreadPoolExecutor的内部类，所以需要new
         */
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        //执行任务
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-2
        //pool-1-thread-3
        //pool-1-thread-4
        //pool-1-thread-5
//
        //java.util.concurrent.RejectedExecutionException: Task 第二季_46_线程池.ThreadPoolDemo$$Lambda$1/824009085@2aae9190 rejected from java.util.concurrent.ThreadPoolExecutor@2f333739[Running, pool size = 5, active threads = 4, queued tasks = 0, completed tasks = 4]

        //注意：执行结果不唯一
        //因为触发了拒绝策略
    }

    //自定义线程池
    //new ThreadPoolExecutor.CallerRunsPolicy()
    @Test
    public void demo5() {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //执行任务
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //main
        //pool-1-thread-2
        //pool-1-thread-2
        //pool-1-thread-3
        //pool-1-thread-4
        //pool-1-thread-5

        //注意：执行结果不唯一
        //调用者就是main线程，拒绝策略让main线程执行任务
    }

    //自定义线程池
    //new ThreadPoolExecutor.DiscardOldestPolicy()
    @Test
    public void demo6() {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        //执行任务
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-4
        //pool-1-thread-4
        //pool-1-thread-4
        //pool-1-thread-4
        //pool-1-thread-3
        //pool-1-thread-2
        //pool-1-thread-5

        //注意：执行结果不唯一
        //只执行了8个任务，有两个被抛弃了
    }

    //自定义线程池
    //new ThreadPoolExecutor.DiscardPolicy()
    @Test
    public void demo7() {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        //执行任务
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        //输出结果：
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1
        //pool-1-thread-1

        //注意：执行结果不唯一
        //只执行了4个任务，6个任务被丢弃了
    }
}
