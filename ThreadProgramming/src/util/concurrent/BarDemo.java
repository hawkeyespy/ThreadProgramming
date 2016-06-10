package util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarDemo {

	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
		
		System.out.println("Starting");
		
		new MyThreadBar(cb, "A");
		new MyThreadBar(cb, "B");
		new MyThreadBar(cb, "C");
		new MyThreadBar(cb, "X");
		new MyThreadBar(cb, "Y");
		new MyThreadBar(cb, "Z");		
	}
}

class MyThreadBar implements Runnable
{
	CyclicBarrier cb;
	String name;
	MyThreadBar(CyclicBarrier cb, String name)
	{
		this.cb = cb;
		this.name = name;		
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println(name+" is waiting for barrier");
		
		try {
			this.cb.await();
			System.out.println(name+" has crossed the barrier");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}

class BarAction implements Runnable
{
public void  run()
{
	System.out.println("Barrier Reached");
}
}