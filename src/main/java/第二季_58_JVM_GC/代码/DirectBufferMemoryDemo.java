package 第二季_58_JVM_GC.代码;

import org.junit.Test;

import java.nio.ByteBuffer;

public class DirectBufferMemoryDemo {
    //演示OutOfMemoryError: Direct buffer memory
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
    @Test
    public void demo() {
        ByteBuffer.allocateDirect(10 * 1024 * 1024);
        //抛出错误
        //java.lang.OutOfMemoryError: Direct buffer memory
    }
}
