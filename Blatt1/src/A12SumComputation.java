package Blatt1;
//package threadslernen;

import java.util.Date;

public class A12SumComputation {
	final static int SIZE = 100000;
	private int sum = 0; //gemeinsam benutzte Rescource --> Race-Condition...
	private int[] sa1 = new int[SIZE];
	private int[] sa2 = new int[SIZE];
	private long starttime = System.currentTimeMillis();
	private long endtime;

	public static void main(String[] args) {
		A12SumComputation prog = new A12SumComputation();
		prog.execute(args);
	}

	// Methode zum Zugriff auf sum
	synchronized void addToSum(int i) {   //Synchronisierte Methode --> da sum sonst Race-Cond...
		sum = sum + i;
	}

	private void execute(String[] args) {
		System.out.println("Starte Programm A12SumComputation");
		// Initialisiere bei sa1 alle Elemente mit 1, bei sa2 mit 2
		for (int i = 0; i < sa1.length; i++) {
			sa1[i] = 1;
			sa2[i] = 2;
		}

		Thread worker1 = new A12Wroker(1, sa1); // Erzeuge Worker-Thread 1
		Thread worker2 = new A12Wroker(2, sa2);// Erzeuge worker-Thread 2
		worker1.start(); // Starte Worker-Thread 1
		worker2.start(); // Starte Worker-thread 2
		try {
			worker1.join();// Warte auf das Ende von Worker-Thread 1
			worker2.join();// Warte auf das Ende von Worker-Thread 2
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//...
		System.out.println("Programm: sum = " + sum);
		System.out.println("Beende Programm A12SumComputation");
		endtime = System.currentTimeMillis();
		System.out.println("abgelaufene Zeit: "+(endtime-starttime)+"ms");
	} // execute

	
	// Implementiere eine private Klasse A12Worker für die Worker-Threads
	private class A12Wroker extends Thread {

		private int id;
		private int[] sa;

		private A12Wroker(int id, int[] sa) {
			this.id = id;
			this.sa=sa;
		}

		//Hauptmethode des Worker-Threads implementieren
		@Override
		public void run() {
			System.out.println("Starte worker " + id + " der Klasse " + getClass().getName());
			// Thread soll 1 Sekunde schlafen
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Thread addiert die Zahlen seines Arrays einzeln zur Gesamtsumme mit
			// Hilfe von addToSum()
			for (int i : sa) {
				addToSum(i);
			}
			
			// vor dem Ende des Worker-Threads die Ergebnisausgabe
			System.out.println("Worker " + id + ": sum = " + sum);
			System.out.println("Beende worker " + id + " der Klasse " + getClass().getName());
		}
			//id auch über currentThread().getId() möglich...
		
		 //synchronized --> 1071ms richtige sum am ende, ohne --> 1020ms falsche sum am ende 
	}
}

// class