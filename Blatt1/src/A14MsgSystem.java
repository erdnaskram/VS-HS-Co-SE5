package Blatt1;
//package threadslernen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class A14MsgSystem {
	
	public static int groeßeNachrichtenPuffer;
	public static A14Nachricht[] nachrichtenPuffer;
	public static int lesePos = 0; // nötig für Ringspeicher
	public static int schreibePos = 0; // nötig für Ringspeicher
	public static boolean stopAnnahme = false; // Stop-Flag für die Annahme von Nachrichten

	public static void main(String[] args) { // Größe Nachrichtenpuffer in args mitgeben z.B 5!!!
		
		long starttime = System.currentTimeMillis();
		long endtime;

		groeßeNachrichtenPuffer = Integer.parseInt(args[0]);

		nachrichtenPuffer = new A14Nachricht[groeßeNachrichtenPuffer]; // Puffer für Nachrichten

		Thread sender1 = new SenderThread("Sender 1");
		Thread empfaenger1 = new EmpfaengerThread("Empfaenger 1");
		
		Thread sender2 = new SenderThread("Sender 2");
		Thread empfaenger2 = new EmpfaengerThread("Empfaenger 2");
		
		sender1.start();
		empfaenger1.start();
		sender2.start();
		empfaenger2.start();
		System.out.println("Geben Sie eine Nachricht ein und drücken Sie Enter: ");
		Scanner sc = new Scanner(System.in);

		sc.nextLine();
		stopAnnahme = true; // da Eingabe getätigt wurde, wird die Annahme von Nachrichten gestoppt
		try {
			sender1.interrupt(); // stoppen Sender, da er keine Nachrichten mehr in den Puffer schreiben darf
			sender2.interrupt(); // sobald etwas in die Console eingegeben wurde
			
		
			sender1.join(); // auf Sender und Empf warten
			sender2.join();
			
			empfaenger1.join();
			empfaenger2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endtime = System.currentTimeMillis();
		System.out.println("Die Laufzeit beträgt "+ (endtime-starttime)+"ms");

	}

	// synchronized, da alle Threads auf gloable Variablen zugreifen....
	public static synchronized void schreibePuffer(A14Nachricht nachricht) { // nötig für Ringspeicher/Nachrichtenpuffer

		nachrichtenPuffer[schreibePos] = nachricht;
		//System.out.println("SP "+schreibePos);
		if (schreibePos < groeßeNachrichtenPuffer - 1) {
			schreibePos++;

		} else {
			schreibePos = 0;
		}

	}

	public static synchronized ArrayList<Object> lesePuffer() { // nötig für Ringspeicher/Nachrichtenpuffer

		A14Nachricht nachricht = nachrichtenPuffer[lesePos];
		//System.out.println("LP "+lesePos);
		nachrichtenPuffer[lesePos] = null; // nach lesen, Nachricht wieder löschen
		if(nachricht!=null) { //nur lesePos erhöhen falls Nachricht im Puffer steht 
			if (lesePos < groeßeNachrichtenPuffer - 1) {
				lesePos++;

			} else {
				lesePos = 0;
			}
			
		}
		
		//list speichert das StopFlag zum Zeitpunkt lesens der Nachricht
		ArrayList<Object> list = new ArrayList<>();
		list.add(nachricht);
		list.add(A14MsgSystem.stopAnnahme);
		
		return list;

	}

}

class SenderThread extends Thread {

	public String senderName;

	public SenderThread(String name) {
		this.senderName = name;
	}

	@Override
	public void run() {
		System.out.println("Sender-Thread " + senderName + " gestartet...");

		while (true) {
			if (A14MsgSystem.stopAnnahme == true) { // prüfung ob Annahme durch Eingabe in Konsole gestoppt wurde
				break;
			}
			int rndmZahl = getRandomNumber(0, 29);
			A14Nachricht nachricht = new A14Nachricht(senderName, rndmZahl); // Nachricht mit rdnm Zahl generieren
			try {
				A14MsgSystem.schreibePuffer(nachricht); // neue Nachricht in NachrichtenPuffer speichern
				System.out.println("Sender-Thread " + senderName + " erzeugt Nachricht: " + nachricht
						+ " und speichert sie im NachrichtenPuffer...");
				if (rndmZahl != 0) {
					sleep(rndmZahl * rndmZahl); // Erzeugen/Speichern einer Nachricht dauert eine gewisse Zeit
				}

			} catch (InterruptedException e) {
				break; // anstelle einer Exception soll einfach der Thread aufhören...
			}
			
		}

		System.out.println("Sender-Thread " + senderName + " beendet...");

	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

}

class EmpfaengerThread extends Thread {

	public String empfaengerName;

	public EmpfaengerThread(String name) {
		this.empfaengerName = name;
	}

	@Override
	public void run() {
		System.out.println("Empfaenger-Thread " + empfaengerName + " gestartet...");

		while (true) {
			ArrayList<Object> nachrichtenListe = A14MsgSystem.lesePuffer();
			//Liste: stelle 0 = nachricht , stelle 1 = StopAnnahme-Flag

			try {
				Date date = new Date();
				long t = date.getTime(); // aus Angabe gegeben....
				long dauer = t % 1000;
				System.out.println("Empfaenger-Thread " + empfaengerName + " holt Nachricht: " + nachrichtenListe.get(0)  
						+ " aus NachrichtenPuffer..."); 
				//System.out.println("Nachrichtenpuffer:" + Arrays.deepToString(A14MsgSystem.nachrichtenPuffer));
				if (dauer != 0) {
					sleep(dauer); // holen und verarbeiten einer nachricht dauert eine gewisse Zeit
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Prüfung ob alle Nachrichten gelesen wurden und Annahme von Nachrichten
			// gestoppt wurde durch Eingabe in Konsole
			if (nachrichtenListe.get(0) == null && (boolean)nachrichtenListe.get(1) == true) {    // Empfänger kann noch weiterlesen bis alle nachrichten == null sind ...
				break; // aus while-Schleife springen
			}

		}

		System.out.println("Empfaenger-Thread " + empfaengerName + " beendet...");

	}
	
	
	
	
	//TO-DO: Beim empfänger auf Null nachricht vor ausgabe prüfen

}