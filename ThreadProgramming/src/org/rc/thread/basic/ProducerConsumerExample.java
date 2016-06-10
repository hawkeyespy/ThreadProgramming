package org.rc.thread.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerExample {

	public static void main(String[] args) {
		final Process process = new Process();
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				try {
					process.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				try {
					process.consume();
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

class Process
{
	List<Integer> list = new ArrayList<Integer>();
	int LIMIT = 10;
	Object lock = new Object();
	Random random = new Random();
	
	public void produce() throws InterruptedException
	{
		int value = 0;
		while(value<LIMIT)
		{
			synchronized (lock) {
				System.out.println("added new item");
				list.add(value++);
				lock.notify();
				System.out.println("Notify from producer.");
				
				lock.wait();
				System.out.println("produceer is waiting.");
				
			}
		}
	}
	
	public void consume() throws InterruptedException
	{
		int value =0;
		Thread.sleep(1000);
		while(value<LIMIT)
		{
			synchronized (lock) {
				System.out.println("consumer is waiting.");
				lock.notify();
				System.out.println("consumer is removing.");
				System.out.println(list.remove(0));
				System.out.println("consumer is notifying.");
				lock.wait();
				value++;
			}
		}
	}
}