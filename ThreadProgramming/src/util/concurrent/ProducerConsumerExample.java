package util.concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerExample {
	static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(
			10);
	static Random random = new Random(100);

	public static void producer() {
		while (true) {
			try {
				queue.put(random.nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void consumer() {
		while (true) {
			try {
				Thread.sleep(100);
				Integer value;
				if (random.nextInt(10) == 0) {
					value = queue.take();
					System.out.println("value taken from queue: " + value
							+ " queue size is :" + queue.size());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				producer();
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				consumer();
			}

		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
