package org.rc.thread.basic;

public class Volatile {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
	
		Counter counter = new Counter();
		counter.start();
		Thread.sleep(100);
		
		counter.shutDown();

	}

}

class Counter extends Thread
{
	// volatile keyword make sure that value of runThread will always be read
	// and not be cached for a object.
	// if runThread is not made volatile, on some systems java may cache
	// runThread as true in run method and it will be infinite loop even after
	// making call to shutdown method. 
	private volatile boolean runThread = true;
	//	private boolean runThread = true;
	public void run()
	{
		while(runThread)
		{
			System.out.println("Thread running");
		}
		System.out.println("Thread shut down");	
	}
	
	public void shutDown()
	{
		runThread = false;
	}
}