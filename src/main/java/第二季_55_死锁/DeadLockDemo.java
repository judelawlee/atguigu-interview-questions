package 第二季_55_死锁;

import org.junit.Test;

public class DeadLockDemo {
    /**
     * 我知道了，之前有的多线程执行结果跟视频里不一样
     * 是因为视频里是放在main()里执行，而我是在单元测试里执行
     */
    public static void main(String[] args) {
        String lock1 = "lock1";
        String lock2 = "lock2";

        new Thread(new DeadLock(lock1,lock2),"A线程").start();
        new Thread(new DeadLock(lock2,lock1),"B线程").start();

        //输出结果：
        //A线程持有lock1锁，像获得lock2锁
        //B线程持有lock2锁，像获得lock1锁

        //在IDEA自带的Terminal里调用jps -l命令查看进程（可以类比Linux里的ps命令）
        //看到有一个 2464 第二季_55_死锁.DeadLockDemo 还在运行，说明程序没有执行完

        //再调用jstack 2464 查看打印出来的日志
        //Java stack information for the threads listed above:
        //===================================================
        //"B线程":
        //        at 第二季_55_死锁.DeadLock.run(DeadLock.java:22)
        //        - waiting to lock <0x000000076b90b7a0> (a java.lang.String)
        //        - locked <0x000000076b90b7d8> (a java.lang.String)
        //        at java.lang.Thread.run(Thread.java:748)
        //"A线程":
        //        at 第二季_55_死锁.DeadLock.run(DeadLock.java:22)
        //        - waiting to lock <0x000000076b90b7d8> (a java.lang.String)
        //        - locked <0x000000076b90b7a0> (a java.lang.String)
        //        at java.lang.Thread.run(Thread.java:748)
        //
        //Found 1 deadlock.
    }
}
