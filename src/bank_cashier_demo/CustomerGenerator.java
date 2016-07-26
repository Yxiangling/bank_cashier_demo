package bank_cashier_demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by 110 on 2016/7/26.
 */
public class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random rand  = new Random(27);

    public  CustomerGenerator(CustomerLine cq){
        customers = cq;
    }

    public void run(){
        try{
            while(!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                customers.put(new Customer(rand.nextInt(1000)));
            }
        }catch(InterruptedException e){
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}
