import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Cocowwy
 * @create 2020-09-09-22:33
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        seeOKByVolatile();

        /**
         * volatile不保证原则性的验证
         * 什么是原子性？
         * 不可分割，完整性，也即某个线程正在做某个具体业务的时，中间不可以被加塞或者被分割，需要整体完整，
         * 要么同时成功，要么同时失败。
         *
         * 如何解决原子性的问题？
         *   1.synchronized
         *   2.AtomicInteger
         *     getAndIncrement() ===> Atomically increments by one the current value. 原子性的给当前值加1
         *
         *
         */
        Data data = new Data();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for(int j=0;j<1000;j++){
                    //上面为验证原子性  下面为使用AtomicInteger来保证原子性的操作
                    data.addPlus();
                    data.addAtomicInteger();
                }
            }, "Thread" + i).start();
        }

        //需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值看是多少

        //两种方法 一个是main线程 一个是gc线程
        while (Thread.activeCount()>2){
            Thread.yield();
        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println(Thread.currentThread().getName()+"线程获取的number值为"+data.number);
        System.out.println(Thread.currentThread().getName()+"线程获取的atomicInteger值为"+data.atomicInteger);
    }


    /**
     * volatile可见性的代码验证
     * 当不使用volatile关键字, 此时我们会发现main的while一直在死循环
     * 当使用volatile关键字,当thread一改变值，while循环就结束
     * <p>
     * volatile可以保证可见性，即使通知其他线程，主物理内存的值已经被修改
     * 每次都是9xxx多 所以不保证原子性  当方法加上synchronized后 就能每次输出10000了
     */
    private static void seeOKByVolatile() {
        Data data = new Data();

        //线程thread1
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.add();
            System.out.println(Thread.currentThread().getName() + "\t update number");
        }, "thread1").start();

        //线程main
        while (data.number == 0) {
            //当number的值一直为0则死循环
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is down main get number:" + data.number);
    }
}


class Data {
    volatile int number = 0;

    /**
     * 原子类
     */
    AtomicInteger atomicInteger=new AtomicInteger(0);

    public void add() {
        this.number = 60;
    }

    public  void  addPlus() {
        number++;
    }

    /**
     * 原子的自增操作
     */
    public void addAtomicInteger(){
        atomicInteger.getAndIncrement();
    }
}
