/**
 * @author Cocowwy
 * @create 2020-09-09-18:46
 */
public class SingletonDemo {

    /**
     * 排除指令重排的情况
     */
    private volatile static SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println("初始化构造器！");
    }

    /**
     * 双重锁校验 加锁前和加锁后都进行判断
     */
    public static SingletonDemo getInstance() {
        if (null == instance) {
            synchronized (SingletonDemo.class) {
                if (null == instance) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SingletonDemo.getInstance();
                }
            }, i + "").start();
        }
    }
}
