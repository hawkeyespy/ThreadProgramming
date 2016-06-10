package org.rc.thread.basic;

public class SynchronizeExample {
	int count;

	/**
	 * If we remove the synchronized keyword output may not be 2000. To make
	 * sure the output should always be 2000, increment() method must be
	 * synchronized.
	 * 
	 * output without synchronized: Value of count : 1974
	 * output with synchronized: Value of count : 2000 
	 * 
	 */
	public synchronized void increment() {
		count++;
	}

	public void process() {

	}

	public static void main(String[] args) {
		SynchronizeExample obj = new SynchronizeExample();
		obj.doWork();
	}

	public void doWork() {
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 1000; i++) {
					increment();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 1000; i++) {
					increment();
				}
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

		System.out.println("Value of count : " + count);
	}
}
