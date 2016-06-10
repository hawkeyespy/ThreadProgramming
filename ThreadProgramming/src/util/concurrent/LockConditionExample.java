package util.concurrent;



import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionExample {
	public static void main(String[] args) {
		final Runner runner = new Runner();
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (Exception e) {
		}
		runner.finished();
	}

}

class Runner {
 
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
 
    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
 
    public void firstThread() throws InterruptedException {
        lock.lock();
         
        System.out.println("Waiting ....");
        cond.await();
         
        System.out.println("Woken up!");
 
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }
 
    public void secondThread() throws InterruptedException {
         
        Thread.sleep(1000);
        lock.lock();
         
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
         
        cond.signal();
        System.out.println("finish this job first");
        try {
            increment();
            System.out.println("finished this job first");
        } finally {
            lock.unlock();
        }
    }
    
 
    public void finished() {
        System.out.println("Count is: " + count);
    }
}