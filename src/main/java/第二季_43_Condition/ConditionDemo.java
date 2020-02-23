package 第二季_43_Condition;

import org.junit.Test;

//视频P44
//题目：线程之间按顺序调用，实现A->B->C顺序
//A线程打印5次，B线程打印10次，C线程打印5次
//这样重复10轮
public class ConditionDemo {
    @Test
    public void demo() {
        SharedData data = new SharedData();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.aPrint();
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.bPrint();
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.cPrint();
            }
        }, "线程C").start();

        //输出结果：
        //线程A0
        //线程A1

        //这个结果肯定是错误的，但是代码明明跟视频里的一样
    }
}
