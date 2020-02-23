> 视频P65到P71

# 3 工作中用过的JVM常用基本配置参数有哪些

![JVM里的各个区.png](http://ww1.sinaimg.cn/large/005IGVTXly1gbz713qkgdj31hc0u0qul.jpg)

```java
//返回JVM中的内存总量，对应-Xms，默认是系统内存的1/64
Runtime.getRuntime().totalMemory();
//返回JVM试图使用的最大内存量，对应-Xmx，默认是系统内存的1/4
Runtime.getRuntime().maxMemory();
```

JVM参数在IDEA的run -> Edit configuration -> VM options中设置

- -Xms：默认是系统内存的1/64，等价于-XX:InitialHeapSize
- -Xmx：默认是系统内存的1/4，等价于-XX:MaxHeapSzie
- -Xss：设置单个线程栈的大小，一般默认512k到1024k，等价于-XX:ThreadStackSizze。使用命令打印出来发现居然取值是0，0表示使用默认值，这个默认值取决于不同的系统，而不是0Mb 
- -Xmn：设置年轻代的大小，一般不用调整
- -XX:MetaspaceSize：设置元空间的大小
  - 元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代之间最大的区别在于：元空间并不在虚拟机内存中，而是使用本地内存。因此，默认情况下，元空间的带线啊哦仅受本地内存限制
- -XX:+PrintGCDetails：打印GC日志信息

![GC日志详细信息.png](http://ww1.sinaimg.cn/large/005IGVTXly1gc0chbgjxrj31hc0u01gk.jpg)

格式：GC前该区的内存占用->GC后该区的内存占用（该区总内存大小）

- -XX:SurvivorRatio：设置新生代中eden区和s0/s1空间的比例，默认是8：1：1。如果这个值设为4，比例就是eden：s0：s1=4：1：1

- -XX:NewRatio：设置老年代和新生代的空间比例，默认是2：1。设置为4，就是老年代：新生代=4：1

- -XX:MaxTenuringThreshold：设置垃圾最大年龄，就是一个对象在新生代的from区和to区来回的次数，超过这个次数就能升到老年代的GC次数，默认值是15。取值必须在0到15之间