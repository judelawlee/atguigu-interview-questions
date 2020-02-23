package 第二季_25_锁.代码;

import org.junit.Test;

public class ReadWriteLockDemo {
    public void demo() {
        Cache cache = new Cache();

        //十个线程负责写入
        for (int i = 0; i < 5; i++) {
            final int temp = i;//因为lambda里要求使用的变量为final，这里只好单独建一个final变量
            new Thread(() -> {
                cache.put(String.valueOf(temp), String.valueOf(temp));
            }).start();
        }

        //十个线程负责读取
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                cache.get(String.valueOf(temp));
            }).start();
        }

        //输出结果：
        //线程Thread-2开始写入，key=2
        //线程Thread-3开始写入，key=3
        //线程Thread-1开始写入，key=1
        //线程Thread-4开始写入，key=4
        //线程Thread-0开始写入，key=0
        //线程Thread-0写入完成，key=0
        //线程Thread-4写入完成，key=4
        //线程Thread-1写入完成，key=1
        //线程Thread-3写入完成，key=3
        //线程Thread-2写入完成，key=2
        //线程Thread-5开始读取，key=0
        //线程Thread-5读取完成，key=0
        //线程Thread-6开始读取，key=1
        //线程Thread-6读取完成，key=1
        //线程Thread-7开始读取，key=2
        //线程Thread-7读取完成，key=2
        //线程Thread-8开始读取，key=3
        //线程Thread-8读取完成，key=3
        //线程Thread-9开始读取，key=4
        //线程Thread-9读取完成，key=4
    }

    public static void main(String[] args) {
        new ReadWriteLockDemo().demo();
    }
}
