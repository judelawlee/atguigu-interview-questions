> 视频P42

# 1 构成

- synchronized关键字属于JVM层面，底层是通过monitor对象来完成，wait/notify等方法也依赖于monitor对象。monitorenter、monitorexit
- Lock是Java的一个类，是api层面的锁

# 2 使用方法

- synchronized不需要用户手动释放锁，代码执行完后系统会自动让线程释放锁对象的占用
- Lock需要用户手动释放锁，这有可能会导致死锁的现象

# 3 是否可以中断

- synchronized不可中断，除非抛出异常或者正常运行完成
- Lock可中断
  - 1.设置禅师方法，tryLock(long timeout, TimeUnit unit)
  - lockInterruptibly放代码块中，调用interrupt()方法可中断

# 4 加锁是否公平

- synchronized是非公平锁
- Lock可以通过参数设置，无参时为非公平锁

# 5 锁绑定多个Condition

- synchronized没有这种机制
- Lock可以实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized那样要么随机唤醒一个线程要么唤醒全部线程