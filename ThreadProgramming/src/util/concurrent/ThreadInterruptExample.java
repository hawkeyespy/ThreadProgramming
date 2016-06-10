package util.concurrent;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadInterruptExample {

	public static void main(String[] args) throws InterruptedException {
		Date date =new Date();
        System.out.println(date);
        System.out.println("Starting.");
 
        ExecutorService exec = Executors.newCachedThreadPool();
 
        Future<?> fu = exec.submit(new Callable<Void>() { 
          
            public Void call() throws Exception {
                Random ran = new Random();
 
                for (int i = 0; i < 1E8; i++) {
 
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        return null;
                    }
 
                    Math.sin(ran.nextDouble());
                }
                System.out.println("Call method completed");
                return null;
            }
 
        });
         
        exec.shutdown();
         
         
        Thread.sleep(500);
         
        //exec.shutdownNow();
        fu.cancel(true);
         
        exec.awaitTermination(1000, TimeUnit.SECONDS);
         
        System.out.println("Finished.");
        
        //Interrupting normal runnable thread.
        
        Thread t1 = new Thread(new Runnable(){

			public void run() {
				Random ran = new Random();
				 
                for (int i = 0; i < 1E8; i++) {
 
                	//Unless we check for the flag in run method, thread will not interrupt.
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        return;
                    }
 
                    Math.sin(ran.nextDouble());
                }				
                System.out.println("Run method completed");
                
			}
			
        	
        });
        t1.start();
        Thread.sleep(500);
		// Thread. interrupt() just make the interrupt flag true. If run method
		// does not check this flag and exit. This call can not force stop the
		// thread execution. 
        t1.interrupt();
    }
}
