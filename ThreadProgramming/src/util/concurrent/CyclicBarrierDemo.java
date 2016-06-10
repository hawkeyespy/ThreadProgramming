package util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
public static void main(String[] args) {
	CyclicBarrier cb = new CyclicBarrier(3, new Runnable(){
		public void run()
		{
			System.out.println("Barrier Reached");
		}
	});
	
	System.out.println("Starting");
	new MyWorkThread(cb, "A");
	new MyWorkThread(cb, "B");
	new MyWorkThread(cb, "c");
}
}

class MyWorkThread implements Runnable
{
	CyclicBarrier cbar;
	String name;
	
	MyWorkThread(CyclicBarrier c, String s)
	{
		cbar = c;
		name = s;
		new Thread(this).start();
	}
	
	public void run()
	{
		System.out.println(name);
		
		try{
			cbar.await();
		}
		catch(BrokenBarrierException exc)
		{
			System.out.println(exc);
		}
		catch(InterruptedException ex)
		{
			System.out.println(ex);
		}
	}
}
