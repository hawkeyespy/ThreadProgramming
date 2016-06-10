package util.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {

	public static void main(String[] args) {
		final RunnerClass runner = new RunnerClass();
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				try {
					for(int i=0; i<10;i++)
					{
						runner.firstThread();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				try {
					for(int i=0; i<10;i++)
					{
						runner.secondThread();
					}
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
//		runner.finished();
	}

	
}
/**
 * This Class demonstrate how multiple threads can lead to deadlock.
 * @author Ravi Chandra
 *
 * Jun 14, 2014 4:15:45 AM
 */
class RunnerClass {
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	public void firstThread() throws InterruptedException {
		lock1.lock();
		lock2.lock();
		System.out.println("from Thread 1");
		Thread.sleep(1000);
		// It is necessary to release all the locks acquired, otherwise it will
		// lead to deadlock condition. If we comment one of the unlock statement
		// this code will be in deadlock.
		lock1.unlock();
		lock2.unlock();
	}

	public void secondThread() throws InterruptedException {
		// The sequence which lock is acquire matters, it may happen that two
		// different threads may have taken one lock each and waiting for
		// another lock.
		lock2.lock();
		lock1.lock();
		System.out.println("from Thread 2");
		Thread.sleep(1000);
		lock1.unlock();
		lock2.unlock();
	}
}
