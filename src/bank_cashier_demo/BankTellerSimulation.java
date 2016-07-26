package bank_cashier_demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
/**
 * Created by 110 on 2016/7/26.
 */
public class BankTellerSimulation {
    static final int SIZE = 50;//顾客队列的最大长度
    static final int PERIOD = 1000;//调整时间间隔
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customerLine = new CustomerLine(SIZE);
        exec.execute(new CustomerGenerator(customerLine));
        exec.execute(new TellerManager(exec,customerLine,PERIOD));
        System.out.println("Press 'Enter' to exit");
        System.in.read();
        exec.shutdownNow();
    }
}
