package bank_cashier_demo;

/**
 * Created by 110 on 2016/7/26.
 */

public class Customer {
    private final int serviceTime;

    public Customer(int tm){
        serviceTime = tm;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public String toString(){
        return "["+serviceTime+"]";
    }
}
