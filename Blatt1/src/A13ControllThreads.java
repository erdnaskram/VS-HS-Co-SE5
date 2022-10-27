package Blatt1;
//package threadslernen;

import java.util.Date;
import java.util.Random;

public class A13ControllThreads {

	public static void main(String[] args) {

		long starttime = System.currentTimeMillis();
		long endtime;

		Thread worker1 = new A13Worker("Worker1", 10);
		Thread worker2 = new A13Worker("Worker2", 10);

		worker1.start();
		worker2.start();

		try {
			Thread.sleep(3000);
			worker1.interrupt();
			Thread.sleep(1000);
			worker2.interrupt();
			worker1.join();
			worker2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endtime = System.currentTimeMillis();
		System.out.println(" Verstrichene Zeit: " + (endtime - starttime));

	}
}

// Implementiere eine private Klasse A13Worker für die Worker-Threads
class A13Worker extends Thread {

	String name;
	int zyklusWartezeit;

	public A13Worker(String name, int zyklusWartezeit) {
		this.name = name;
		this.zyklusWartezeit = zyklusWartezeit;
	}

	@Override
	public void run() {
		System.out.println(name + " gestartet...");
		boolean nichtGefunden;
		int rndmZahl = 0;
		int counter = 0;
		boolean isInterrupted = false;
		while (true) {
			counter++;
			nichtGefunden = true;
			while (nichtGefunden) {
				Random rnd = new Random(new Date().getTime());
				rndmZahl = rnd.nextInt();

				if (isPrimeNumber(rndmZahl)) {
					nichtGefunden = false;
				}

			}
			
			System.out.println(name + "--> Primzahl: " + rndmZahl + " Schleifennummer: " + counter);
			try {
				sleep(zyklusWartezeit);
			} catch (InterruptedException e) {
				System.out.println(name+" beendet während sleep");
				break; //hier auch break, da wenn sleep nur wegen interrupt auf einen fehler trifft --> schleife kann geschlossen werden 

			}
			isInterrupted = currentThread().isInterrupted(); // true wenn interrupted
			if(isInterrupted) {
				System.out.println(name+" beendet nach sleep");
				break; //raus aus while schleife
			}
			
		}
		System.out.println(name + " beendet...");

	}

	private boolean isPrimeNumber(int num) { // Methode könnte auch in Hauptklasse implementiert werden
		/*
		 * korrekter, aber schlechter Algorithmus, weil langsam. Soll aber für Aufgabe
		 * langsam sein!
		 */
		if (num < 2)
			return false;
		for (int i = 2; i < num; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	} // isPrimeNumber()

}
