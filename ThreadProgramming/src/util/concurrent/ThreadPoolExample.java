package util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newFixedThreadPool(2);

		for (int i = 1; i < 6; i++) {
			threadPool.submit(new Worker(i));
		}

		threadPool.shutdown();

		System.out.println("All threads are subitted");
		try {
			threadPool.awaitTermination(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed all threads");
	}
}

class Worker implements Runnable {
	private int id;

	Worker(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Starting thread id : " + id);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed thread id :" + id);
	}

}
