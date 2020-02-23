> 视频P81到P87

# StackOverflowError

常见错误原因：方法深度调用，多是递归引起，方法调用次数太多导致超出栈内存

# OutOfMemoryError: Java heap space

堆内存溢出

主要是对象占用空间超过堆内存空间

# OutOfMemoryError: GC overhead limit exceeded

这个错误表示大量资源被拿去做GC了，而GC的效果却不怎么好。具体定义是超过98%的时间用来做GC，却回收了不到2%的堆内存，并且连续多次出现这种情况，就会抛出这个异常

# OutOfMemoryError: Direct buffer memory

原因：**NIO**（指new IO，而不是网络的那个NIO）中常使用ByteBuffer来读取或者写入数据，这是一种基于通道（channel）与缓冲区（buffer）的IO方式。它可以使用native函数库直接分配堆外内存。然后通过一个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。这样能在一些场景中显著提高性能，因为避免了在Java堆和native堆中来回赋值数据。

ByteBuffer.allocate()：分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相对较慢

ByteBuffer.allocteDirect()：分配操作系统本地内存，不属于GC管辖范围，由于不需要内存拷贝所以速度相对较快

但如果不断分配本地内存，堆内存很少使用，那么JVM就不需要执行GC，DirectByteBuffer对象们就不会被回收，这时候堆内存充足，但本地内存可能已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError

# OutOfMemoryError: unable to create new native thread

原因：常发生在高并发请求服务器的时候，准确的讲与平台、系统有关

- 应用创建了太多线程，一个应用进程创建多个线程，超过系统承载极限
- 服务器并不允许你的应用程序创建这么多线程，Linux默认允许单个进程可以创建的线程数是1024个，如果超过这个数量，就会报错

解决方法：

- 降低创建线程的数量
- 修改Linux服务器配置，扩大默认限制

  `vim /etc/security/limits.d/90-nproc.conf`可以查看，也可以修改

# OutOfMemoryError: Metaspace

JDK8以后用元空间替代永久代，元空间使用本地内存，存放了以下信息：

- 虚拟机加载的类信息

- 常量池
- 静态变量
- 即时编译后的代码