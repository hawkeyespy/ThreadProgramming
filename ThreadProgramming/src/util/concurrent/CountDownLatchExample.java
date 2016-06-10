package util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(3);

		for (int i = 1; i < 4; i++) {
			executor.submit(new Processor(i, latch));
		}
		executor.shutdown();

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Completed all threads");
	}
}

class Processor implements Runnable {
	int id;
	CountDownLatch latch;

	Processor(int id, CountDownLatch latch) {
		this.id = id;
		this.latch = latch;
	}

	public void run() {
		System.out.println("Started thread id : " + id);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		latch.countDown();
	}

}