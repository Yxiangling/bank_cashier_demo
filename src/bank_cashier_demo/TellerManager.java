package bank_cashier_demo;

import java.util.concurrent.*;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Created by 110 on 2016/7/26.
 */
public class TellerManager implements Runnable {
    private ExecutorService exec;  //负责启动Teller线程
    private CustomerLine customerLine;

    //按服务顾客数由少到多优先的优先队列，用来进行调度
    //每次都取出服务顾客数最少的出纳员来进行服务，以保证公平性。
    private PriorityQueue<Teller> workingTellers
            = new PriorityQueue<>();

    //正在做其它事情的Teller队列
    private Queue<Teller> tellersDoingOtherThings
            = new LinkedList<Teller>();

    private int adjustmentPeriod; //调度时间

    private static Random rand = new Random();

    public TellerManager(ExecutorService exec,CustomerLine
            customerLine,int adjustmentPeriod){
        this.exec =exec;
        this.customerLine = customerLine;
        this.adjustmentPeriod = adjustmentPeriod;

        //在构造器中先分配一个Teller进行服务
        Teller teller = new Teller(customerLine);
        exec.execute(teller);
        workingTellers.add(teller);
    }

    //通过当前customerLine中的顾客数以及正在工作的Teller
    //人数的比例关系，来确定是否要加/减Teller的数目
    public void adjustTellerNumber(){

        //如果customerLine队列过长，则增加服务的Teller
        if(customerLine.size()/workingTellers.size()>2){

            //如果在做其它事的Teller则从中抽调出人来,否则重新分配一个Teller
            if(tellersDoingOtherThings.size()>0){
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.add(teller);
                return;
            }
            //重新分配一个Teller
            Teller teller = new Teller(customerLine);
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }

        //当前Tellers过多时，抽调一些去做其它工作
        if(workingTellers.size()>1&&customerLine.size()/workingTellers.size()<2){

            reassignOneTeller();

            //如果这里只有没有customer需要服务，则只需留下一个Teller
            if(customerLine.size()==0){
                while(workingTellers.size()>1){
                    reassignOneTeller();
                }
            }
        }
    }

    private void reassignOneTeller() {
        //从工作队列中取出一个Teller来
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();//让他去做其它工作
        tellersDoingOtherThings.offer(teller);
    }

    public void run(){
        try{
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);

                //按当前情况进行动态调整
                adjustTellerNumber();

                //打印当前的customerLine和workingTeller的情况
                //从结果可以看到随着customerLine大小的变化，workingTeller
                //的人数也是不断变化的。
                System.out.print(customerLine+"{");
                for(Teller teller: workingTellers){
                    System.out.print(teller.shortString()+" ");
                }
                System.out.println("}");
            }
        }catch(InterruptedException ex){
            System.out.println(this+"通过中断异常退出");
        }
        System.out.println(this+"terminating");
    }

    public String toString(){
        return "TellerManager";
    }
}
