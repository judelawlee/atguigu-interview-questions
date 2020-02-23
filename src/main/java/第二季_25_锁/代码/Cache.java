package 第二季_25_锁.代码;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    private Map<String, String> cache = new HashMap<>();
    //实现了ReadWriteLock接口，但没有实现Lock接口
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //获取
    public String get(String key) {
        String value = null;
        //使用读锁
        lock.writeLock().lock();
        try {
            System.out.println(String.format("线程%s开始读取，key=%s",
                    Thread.currentThread().getName(),
                    key));
            Thread.sleep(1000);
            value = cache.get(key);
            System.out.println(String.format("线程%s读取完成，key=%s",
                    Thread.currentThread().getName(),
                    key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    //写入
    public void put(String key, String value) {
        lock.readLock().lock();
        try {
            System.out.println(String.format("线程%s开始写入，key=%s",
                    Thread.currentThread().getName(),
                    key));
            Thread.sleep(1000);
            cache.put(key,value);
            System.out.println(String.format("线程%s写入完成，key=%s",
                    Thread.currentThread().getName(),
                    key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}
