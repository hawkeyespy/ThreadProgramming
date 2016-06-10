import java.util.concurrent.Semaphore;


public class ThreadDemo 
{
	public static void main(String args[])
	{
		Semaphore sem =  new Semaphore(1);
		new IncThread(sem, "Thread 1");
		new DecThread(sem, "Thread 2");
	}
}

class Shared
{
	static int count = 0;
}

class IncThread implements Runnable
{
	String name;
	Semaphore sem;
	
	IncThread(Semaphore sem, String name)
	{
		this.sem = sem;
		this.name = name;
		 new Thread(this).start();
	}
	
	public void run() {

		System.out.println("Started "+ name);
		
		try{
			System.out.println("waiting for acquire lock");
			sem.acquire();
			
			System.out.println("lock Acquired ");
			for(int count = 0; count<5; count ++)
			{
				Shared.count++;
				System.out.println(name+" : "+Shared.count);
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in thread "+name);
		}
		System.out.println(name+" releasing lock");
		sem.release();
	}	
}

class DecThread implements Runnable
{
	String name;
	Semaphore sem;
	
	DecThread(Semaphore sem, String name)
	{
		this.sem = sem;
		this.name = name;
		 new Thread(this).start();
	}
	public void run() {
		System.out.println("Started "+ name);
		
		try{
			System.out.println("waiting for acquire lock");
			sem.acquire();
			
			System.out.println("lock Acquired ");
			for(int count = 0; count<5; count ++)
			{
				Shared.count--;
				System.out.println(name+" : "+Shared.count);
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in thread "+name);
		}
		System.out.println(name+" releasing lock");
		sem.release();
	}	
}

