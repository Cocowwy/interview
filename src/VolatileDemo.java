/**
 * volatile可见性的代码验证
 *
 * 1.当不使用volatile关键字,此时我们会发现main的while一直在死循环
 * 2.当使用volatile关键字,当thread一改变值，while循环就结束
 *
 * @author Cocowwy
 * @create 2020-09-09-22:33
 */
public class VolatileDemo {
    public static void main(String[] args) {
        Data data=new Data();

        //线程thread1
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.add();
            System.out.println(Thread.currentThread().getName()+"\t update number");
        },"thread1").start();

        //线程main
        while(data.number==0){
            //当number的值一直为0则死循环
        }

        System.out.println(Thread.currentThread().getName()+"\t mission is down main get number:"+data.number);
    }
}


class Data {
    volatile int number = 0;

    public void add() {
        this.number = 60;
    }
}
