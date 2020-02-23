package 第二季_02_volatile.代码;

import org.junit.Test;

/**
 * 验证volatile的可见性
 * 验证volatile不保证原子性
 */
public class VolatileDemo {
    //保证原子性
    @Test
    public void demo1() {
        MyData data = new MyData();

        //创建一个单独的线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            //睡眠
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //执行方法
            data.turnTo60();

            System.out.println(Thread.currentThread().getName() + "结束方法");
        }).start();

        //写一个循环
        while (data.number == 0) {
            //不需要写是实现
        }

        //如果循环能结束，就打印一行
        //1. number变量没有volatile修饰
        //这里子线程工作内存和主内存里的number都变成了60，但主线程的工作内存还是0，所以一直在循环
        //2. number变量有volatile修饰
        //子线程修改了number的值并写到主内存时，会通知其他线程更新number的值
        System.out.println(Thread.currentThread().getName());
    }

    //不保证可见性
    @Test
    public void demo2() {
        MyData data = new MyData();

        //创建10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //每个线程调用1000次自增
                for (int j = 0; j < 1000; j++) {
                    data.autoIncrease();
                }
            }).start();
        }

        //活跃线程大于2的情况下一直循环，这时候上面还有线程没执行完自增
        //2是因为存在主线程和gc线程两个线程
        while (Thread.activeCount() > 2) {
            //这一句加不加都行
            //是让当前线程（还是主线程？）让出来
            Thread.yield();
        }

        //第一次执行输出9165
        System.out.println(data.number);
    }

    //指令重排造成错误的示例
    @Test
    public void demo3() {
        int a = 0;
        boolean flag = false;

        //修改连个变量的值
        a = 1;//语句1
        flag = true;//语句2

        if (flag) {//语句3
            a += 5;
            System.out.println(a);
        }

        //在多线程下
        //正常情况下是语句1，语句2执行完，执行到语句3
        //但是如果进行指令重排，又是多线程
        //可能会先执行语句2，再执行语句1。但是在多线程下，可能线程1执行了语句1但还没执行语句2，线程2去执行了语句3，这时候得到的结果就有问题

    }
}
