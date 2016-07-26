package bank_cashier_demo;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Created by 110 on 2016/7/26.
 */
public class Teller implements Runnable ,Comparable<Teller>{
    private static int counter  =0;
    private final int id = counter++;

    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine cq){
        customers = cq;
    }

    public void run(){
        try{
            while(!Thread.interrupted()){
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized(this){
                    customersServed++;
                    while(!servingCustomerLine)
                        wait();
                }
            }
        }catch (InterruptedException e){
            System.out.println(this + "terminating");
        }
    }

    public synchronized void doSomethingElse(){
        customersServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine(){
        assert !servingCustomerLine:"already serving:"+this;
        servingCustomerLine = true;
        notifyAll();
    }

    public String toString(){
        return "Teller"+id+"";
    }

    public String shortString(){
        return "T"+id;
    }

    public synchronized int compareTo(Teller other) {
        return customersServed < other.customersServed ? -1 : (customersServed == other.customersServed ? 0 : 1);
    }
}
