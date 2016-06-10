package util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {
//public static void main(String[] args) {
//	Semaphore semaphore = new Semaphore(0);
//	semaphore.release();
//	try {
//		semaphore.acquire();
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.println(semaphore.availablePermits());
//}
	
	public static void main(String[] args) throws Exception {
        
        ExecutorService executor = Executors.newCachedThreadPool();
         
        for(int i=0; i < 200; i++) {
            executor.submit(new Runnable() {
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }
         
        executor.shutdown();
         
        executor.awaitTermination(1000, TimeUnit.SECONDS);
    }
}

class Connection {
	 
    private static Connection instance = new Connection();

	// using semaphore we can ensure only(maximum) defined no of threads are
	// running on at a particular time on the code.
    private Semaphore sem = new Semaphore(10, true);
 
    private int connections = 0;
 
    private Connection() {
 
    }
 
    public static Connection getInstance() {
        return instance;
    }
 
    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
 
        try {
            doConnect();
        } finally {
 
            sem.release();
        }
    }
 
    public void doConnect() {
 
        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }
 
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        synchronized (this) {
            connections--;
        }
 
    }
}