package org.rc.thread.basic;

import java.util.Scanner;

public class WaitNotifyExample {
	public static void main(String[] args) {
		final Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				try {
					processor.consume();
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
	}
}

class Processor {

	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer thread running ....");
			wait();
			System.out.println("Resumed.");
		}
	}

	public void consume() throws InterruptedException {

		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);

		synchronized (this) {
			System.out.println("Waiting for return key.");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			notify();
			// notify doesnot yield the lock thats why next two lines will
			// execute before execution of sysot statement after wait() in
			// produce() method.
			System.out.println("Sent notify signal to other threads.");
			Thread.sleep(5000);
		}
	}
}