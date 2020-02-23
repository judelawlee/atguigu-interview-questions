> 视频P90到P100

# 怎么查看默认的垃圾收集器

```shell
java -XX:+PrintCommandLineFlags -version
```

打印出Java的一些初始参数

```shell
-XX:InitialHeapSize=265936448
-XX:MaxHeapSize=4254983168
-XX:+PrintCommandLineFlags
-XX:+UseCompressedClassPointers
-XX:+UseCompressedOops
-XX:-Use LargePagesIndividualAllocation
-XX:+UseParallelGC
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

其中有一个`-XX:+UseParallelGC`

Java的GC回收类型主要有几种：

- UseSerialGC
- UseParallelGC
- UseConcMarkSweepGC
- UseParNewGC
- UseParallelOldGC
- UseG1GC

- 还有一种serial old gc，已经被淘汰

# 垃圾回收器

![垃圾回收器与JVM结构.png](http://ww1.sinaimg.cn/large/005IGVTXly1gc2np07x97j31hc0u0e0z.jpg)

![JVM各区使用的垃圾回收器.png](http://ww1.sinaimg.cn/large/005IGVTXly1gc2qh6hntbj31hc0u01df.jpg)

连线表示如果新生代使用了某个垃圾收集器，那么老年代必须选择与其有连线的垃圾收集器。其中带红叉的表示连线已经废弃

# 我也不知道这一节叫什么

## 参数

- DefNew -> Default New Generation
- Tenured -> Old
- ParNew -> Parallel New Generation
- PSYoungGen -> Parallel Scavenge
- ParOldGen -> Parallel Old Generation

## server/client模式

- 32位windows，不论硬件如何都默认使用Client的JVM模式
- 32位其他操作系统，2G内存且有2个CPU以上时用Server模式，低于该配置还是Client模式
- 64位系统，只有Server模式

- 只需掌握Server模式，Client模式基本不会用

## 新生代

- 串行GC（Serial）/（Serial Copying）

- 并行GC（ParNew）

- 并行回收GC（Parallel）/（Parallel Scavenge）

## 串行收集器：Serial收集器

是一个单线程的收集器，在进行垃圾收集的时候，必须暂停其他所有的工作线程知道它收集结束

串行收集器是最古老、最稳定、效率高（应该指的是垃圾回收的效率）的收集器，只使用一个线程去回收但其在进行垃圾收集过程中可能会产生较长的停顿。虽然在收集垃圾过程中需要暂停其他所有的工作线程，但它简单高效，对于单个CPU的环境来说，没有县城交互的开销可以使它获得最高的单线程垃圾收集效率，因此Serial垃圾收集器依然是JVM运行在Client模式下默认的新生代垃圾收集器

对应的JVM参数是`-XX:+UseSerialGC`

开启后会使用：Serial（新生代）+Serial Old（老年代）的收集器组合

表示：新生代、老年代都会使用串行垃圾收集器，新生代使用复制算法，老年代使用标记整理算法

# 并行收集器：ParNew收集器

 ParNew收集器起始就是Serial收集器的新生代并行多线程版本，最常见的应用场景是配合老年代CMS GC工作，企业的行为和Serial收集器完全一样，ParNew垃圾收集器在垃圾手机过程中同样也要暂停所有其他的工作线程，是很多JVM运行在Server模式下新生代的默认垃圾收集器

JVM参数：-XX:+UseParNewGC启用，只影响新生代，不影响老年代

开启上述参数后，会使用ParNew（新生代）+Serial Old的收集器组合，新生代使用复制算法，老年代使用标记整理算法

JVM参数：-XX:ParallelGCThreads -> 限制线程数量，默认开启和CPU数目相同的线程数

# 并行收集器：Parallel Scanvenge/Parallel

Parallel Scanvenge垃圾收集器类似ParNew也是一个新生代的垃圾收集器，使用复制算法，也是一个并行的多线程的垃圾收集器，俗称吞吐量优先收集器。一句话：串行收集器在新生代和老年代并行化

它关注的重点是：

- 可控制的吞吐量（吞吐量thoughput=运行用户代码的时间/(运行用户代码的时间+垃圾收集时间)）。高吞吐量意味着高效里用CPU的时间，它用于在后台运算而不需要太多的交互任务
- 自适应调节策略也是Parallel Scavenge收集器与ParNew收集器的一个重要区别。自适应调节策略指虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间（-XX:MaxGCPauseMillis）或最大吞吐量

JVM参数：-XX:+UseParallelGC或-XX:UseParallelOldGC（可互相激活），使用Parallel Scanvenge垃圾收集器

开启该参数后：新生代使用复制算法，老年代使用标记整理算法

# Parallel Old收集器(也就是Parallel MSC)

是Parallel Scavenge的老年代版本，使用多线程的标记整理算法，Parallel Old在JDK1.6才开始提供

Parallel Old正是为了在老年代通用提供吞吐量优先的垃圾收集器

JVM参数：-XX:+UseParallelOldGC

设置该参数后，新生代Parallel+老年代Parallel Old

# 并发标记清除 CMS

CMS收集器（concurrent mark sweep，并发标记清除）是一种以获取最短的回收停顿时间为目标的垃圾收集器

适合应用在B/S系统的服务器上，这类系统尤其重视响应速度，希望系统停顿时间最短

CMS非常适合堆内存大、CPU核数多的服务器端应用，也是G1出现之前大型应用的首选收集器

JVM参数：-XX:UseConcMarkSweepGC 开启该参数后会自动将-XX:+UseParNewGC打开

开启该参数后，使用ParNew（新生代）+CMS（老年代）的收集器组合，Serial Old将作为CMS出错时的后备收集器

四个步骤

1. 初始标记（CMS initial mark）：只是标记一下GC Root能直接关联的对象，速度很快，但仍然需要暂停所有的工作线程

2. 并发标记（CMS concurrent mark）：进行GC Root跟踪的过程，和用户线程一起工作，不需要暂停工作线程。是主要标记过程，标记全部对象

3. 重新标记（CMS remark）：为了修正在并发标记初期，阴用户程序继续运行而导致标记产生变动的那一部分对象的标记记录，仍然需要暂停所有的工作线程。由于并发标记时，用户线程依然运行，因此在正式清理前，再做一次修正

4. 并发清除（CMS concurrent sweep）：清除GC Root不可达对象，和用户线程一起工作，不需要暂停工作线程。基于标记结果，直接清理对象。由于好事醉成的并发标记和并发清除过程中，垃圾收集线程和用户线程可以一起并发工作，所以总体来看CMS的内存回收和用户线程是并发执行的

优点：

- 并发停顿低

缺点：

- 并发执行，对CPU资源压力大

  由于并发进行，CMS在收集时与应用线程会同时增加对堆内存的占用，也就是说，CMS必须要在老年代堆内存用完之前完成垃圾回收，否则CMS回收失败时将触发担保机制，串行老年代收集器将会以STW的方式进行一次GC，从而造成较大停顿时间

- 采用的标记清除算法会导致大量的碎片

  标记清除算法无法整理空间碎片，老年代空间会随着应用时长备逐步耗尽，最后将不得不通过担保机制对堆内存进行压缩。JVM参数：-XX:CMSFullGCsBeForeCompaction（默认0，即每次都进行内存整理）来指定多少次CMS收集之后，进行一次压缩的Full GC

# Serial Old/Serial MSC

Serial Old时Serial收集器老年代版本，它同样是单个线程的收集器，使用标记整理算法，这个收集器也主要是运行在Client默认JVM的老年代垃圾收集器

在Server模式下，主要有两个用途（了解）

- 在JDK5之前版本中与新生代Parallel Scavenge收集器搭配使用（Parallel Scavenge+Serial Old）
- 作为老年代中使用CMS收集器的后备方案

# 如何选择垃圾收集器

- 单CPU或小内存，单机程序

-XX:+UseSerialGC

- 多CPU，需要最大吞吐量，如后台计算型应用

-XX:+UseParallelGC或者-XX:+UseParallelOldGC

# 总结
![垃圾收集器的选择.png](http://ww1.sinaimg.cn/large/005IGVTXly1gc3tjdmt6nj31hc0u0nmb.jpg)