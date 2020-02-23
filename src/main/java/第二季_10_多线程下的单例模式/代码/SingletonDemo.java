package 第二季_10_多线程下的单例模式.代码;

/**
 * 先手写一个普通的单例模式
 */
public class SingletonDemo {
    private volatile static SingletonDemo instance;

    //私有化构造函数
    private SingletonDemo() {
        System.out.println("构造函数执行了。。。");
    }

    //获取实例
    public static SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }

        return instance;
    }

    //多线程下的解决方法1，给方法加上synchronized
    public static synchronized SingletonDemo getInstance2() {
        if (instance == null) {
            instance = new SingletonDemo();
        }

        return instance;
    }

    //多线程下的解决方法2，double check lock
    //这个方法有点记不住
    //指令重排会造成有小概率出错
    public static SingletonDemo getInstance3() {
        if (instance == null) {
            //这里面用同步代码块
            //静态方法锁住类的class
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

}


