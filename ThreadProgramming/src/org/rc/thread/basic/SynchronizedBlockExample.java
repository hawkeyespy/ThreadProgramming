package org.rc.thread.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedBlockExample {
	public static void main(String[] args) {
		Worker worker = new Worker();
		worker.main();
	}
}

/*
 * Sample output
 * Output when methods are synchronized 
 * Starting... 
 * Time taken : 4081 
 * size of list1 2000
 * size of list2 2000
 * 
 * Output when blocks are synchronized
 * Starting...
 * Time taken : 2121
 * size of list1 2000
 * size of list2 2000
 */

class Worker {

	/*
	 * Q: Why to use different lock object and not the list object itself? 
 
	 * A: It can be used as locks, but in case these lists are used
	 * somewhere else for reading or writing data in that case if there is a
	 * lock on these objects in current thread will not allow to make
	 * changes elsewhere. So performance will be decreased.
	 */
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();

	private Random random = new Random();

	/*
	 * Q: Why to use synchronized block and not the make complete method
	 * synchronized?
	 * 
	 * A: When we make method synchronized the lock is taken on the object
	 * itself. So no two threads can execute any of the synchronized methods of
	 * the class, even though it is working of different set of data. For
	 * current class if we make both stageOne() and stageTwo() method
	 * synchronized, at a time only one method will execute and 2nd thread will
	 * wait until thread one releases lock. By making synchronized blocks with
	 * two different lock object, two threads can run parallelly executing the
	 * methods with different locks.
	 */
	private void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}

	private synchronized void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}

	private void process() {
		for (int i = 0; i < 1000; i++) {
			stageOne();
			stageTwo();
		}
	}

	public void main() {
		System.out.println("Starting...");
		long start = System.currentTimeMillis();

		Thread t1 = new Thread(new Runnable() {

			public void run() {
				process();
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				process();
			}

		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long stop = System.currentTimeMillis();

		System.out.println("Time taken : " + (stop - start));
		System.out.println("size of list1 " + list1.size());
		System.out.println("size of list2 " + list2.size());
	}
}