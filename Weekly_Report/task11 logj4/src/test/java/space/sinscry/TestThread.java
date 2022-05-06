package space.sinscry;

import org.junit.Test;

public class TestThread {
    public static void main(String args[]){
        MultiThread R1 = new MultiThread("Thread-1");
        R1.start();
        MultiThread R2 = new MultiThread("Thread-2");
        R2.start();
    }
}
