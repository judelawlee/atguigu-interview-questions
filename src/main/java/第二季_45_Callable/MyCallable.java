package 第二季_45_Callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("call()执行了");
        return "call";
    }
}
