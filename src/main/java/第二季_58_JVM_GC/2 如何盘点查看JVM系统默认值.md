> 视频P59到P64

# 2 如何盘点查看JVM系统默认值

- 标配参数
  - -version
  - -help
  - -showversion

示例：java -version

- X参数（了解）
  - -Xint 解释执行
  - -Xcomp 第一次使用就编译成本地代码
  - -Xmixed 混合模式，先编译后执行，默认
- **XX参数**

  - boolean类型
    - 格式：-XX:+(或者-)参数名，其中+表示开启，-表示关闭
    - -XX:+PrintGCDetails -> 打印GC细节
    - -XX:-PrintGCDetails
    - -XX:+UseSerialGC -> 使用串行垃圾回收器
    - -XX:-UseSerialGC
  - K-V类型
    - 格式：-XX:key=value
    - -XX:MetaspaceSize=128m -> 元空间大小
    - -XX:MaxTenuringThreshold=15 -> 经历过15次GC，能升到老年代

- cmd命令：
  - jinfo -flag JVM参数名 进程编号 -> 查看某个参数是否使用（这里的JVM参数名不需要带前面的-XX）
  - jinfo -flags 进程编号 -> 显示所有参数，包括默认的和手动设置的

- 经典参数
  - -Xms等价于-XX:InitialHeapSize -> 初始化堆内存
    - 使用示例：-Xms128m
  - -Xmx等价于-XX:MaxHeapSzie -> 最大堆内存

- 通过JVM命令打印JVM参数
  - **-XX:+PrintFlagsInitial** -> 查看初始默认值，打印信息中如果是`:=`表示这个参数被修改过
    - 格式：java -XX:+PrintFlagsInitial -version或java -XX:+PrintFlagsInitial 
  - **-XX:+PrintFlagsFinal** -> 查看修改过的JVM参数
  - -XX:+PrintCommandLineFlags

- 运行的时候修改JVM参数