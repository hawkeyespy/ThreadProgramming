package util.concurrent;

import java.util.concurrent.Semaphore;

public class SemDemo {

	public static void main(String[] args) {
		Semaphore sem = new Semaphore(1);
		
		
		new DecThread(sem, "B");
		new IncThread(sem, "A");
	}
}

class Shared
{
	static int count = 0;
}

class IncThread implements Runnable
{
	Semaphore sem = null;
	String name = null;
	IncThread(Semaphore sem, String name)
	{
		this.sem = sem;
		this.name = name;
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println("Starting "+ name);
		
		System.out.println(name+" waiting for permit");
		try {
			sem.acquire();
			System.out.println(name+" gets permit");
			for(int i =0; i<5; i++)
			{
				Shared.count++;
				System.out.println(name+" : "+Shared.count);
			}
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		sem.release();
		System.out.println(name+ " released permit");
		
	}
}

class DecThread implements Runnable
{
	Semaphore sem = null;
	String name = null;
	DecThread(Semaphore sem, String name)
	{
		this.sem = sem;
		this.name = name;
		
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println("Starting "+name);		
		
		try {
			sem.acquire();
			System.out.println(name+" gets a permit");
			for(int i =5; i>0; i--)
			{
				Shared.count--;
				System.out.println(name+" : "+Shared.count);
			}
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sem.release();
		System.out.println(name+" releases the permit");
	}
}