package 第二季_55_死锁;

public class DeadLock implements Runnable {
    private String firstLock;
    private String secondLock;

    public DeadLock(String firstLock, String secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        synchronized (firstLock) {
            System.out.println(Thread.currentThread().getName() + "持有" + firstLock + "锁，像获得" + secondLock + "锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName() + "获得了" + secondLock + "锁");
            }
        }
    }
}
