> 视频P35到P41，P44

# 阻塞队列

- 当队列为空时，获取元素的操作将会被阻塞
- 当队列为满时，添加元素的操作将会被阻塞

所谓阻塞，指在某些情况下会挂起线程。一旦满足某些条件，被挂起的线程又会被自动唤醒。

BlockingQueue使我们不用关注什么时候需要阻塞线程什么时候需要唤醒线程。

# BlockingQueue接口

继承自Collection接口

- **ArrayBlockingQueue**：底层是数组的有界阻塞队列
- **LinkedBlockingQueue**：底层是链表的有界阻塞队列（队列上限默认是Integer.MAX_VALUE）
- PriorityBlockingQueue：支持优先级排序的无界阻塞队列
- DelayQueue：使用优先级队列实现的延迟无界阻塞队列
- **SynchronousQueue**：不存储元素的阻塞队列，也即单个元素的队列
- LinkedTransferQueue：底层是链表的无界阻塞队列
- LinkedBlockingDeque：底层是链表的双向阻塞队列

# 方法

| 方法 | 会抛出异常的方法                       | 返回特殊值                              | 阻塞                   | 超时退出           |
| ---- | -------------------------------------- | --------------------------------------- | ---------------------- | ------------------ |
| 插入 | add(e)<br>队列满时添加元素会抛出异常   | offer(e)<br>成功返回true，失败返回false | put(e)<br>队列满时阻塞 | offer(e,time,unit) |
| 移除 | remove()<br>队列空时移除元素会抛出异常 | poll()<br>获取失败返回null              | take()<br>队列空时阻塞 | poll(time,unit)    |
| 查看 | element()<br>返回队首元素              | peek()                                  | 无                     | 无                 |