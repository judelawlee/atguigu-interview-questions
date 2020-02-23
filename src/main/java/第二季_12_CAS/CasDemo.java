package 第二季_12_CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {
    public void demo() {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndIncrement();
    }
}
