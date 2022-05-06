package space.sinscry;

public class MultiThread implements Runnable{
    private Thread t;
    private String threadName;

    MultiThread(String name){
        threadName=name;
        System.out.println("Createing "+threadName);
    }

    public void run(){
        System.out.println("Running" + threadName);
        try{
            for(int i=4;i>0;i--){
                System.out.println("Thread: "+threadName+", "+i);
                //Sleep a while
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            System.out.println("Thread "+threadName+" interrupted.");
        }
        System.out.println("Thread "+threadName+" exiting.");
    }

    public  void start(){
        System.out.println("Starting "+threadName);
        if(t==null){
            t = new Thread(this,threadName);
            t.start();
        }
    }
}
