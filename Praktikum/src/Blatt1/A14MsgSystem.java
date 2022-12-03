package Blatt1;
//package threadslernen;

import java.util.*;

public class A14MsgSystem {
    public static boolean stopAnnahme = false; // Stop-Flag für die Annahme von Nachrichten
    public static boolean alleGelesen = false; // Gelesen-Flag für das Beenden des Lesens

    public static void main(String[] args) { // Größe Nachrichtenpuffer in args mitgeben z.B 5!!!

        long starttime = System.currentTimeMillis();
        long endtime;

        int groesseNachrichtenPuffer = Integer.parseInt(args[0]);


        A14NachrichtenPuffer nachrichtenPuffer = new A14NachrichtenPuffer(groesseNachrichtenPuffer); // Puffer für Nachrichten

        Thread sender1 = new SenderThread("Sender 1", nachrichtenPuffer);
        Thread empfaenger1 = new EmpfaengerThread("Empfaenger 1", nachrichtenPuffer);

        Thread sender2 = new SenderThread("Sender 2", nachrichtenPuffer);
        Thread empfaenger2 = new EmpfaengerThread("Empfaenger 2", nachrichtenPuffer);

        sender1.start();
        empfaenger1.start();
        sender2.start();
        empfaenger2.start();
        System.out.println("Geben Sie eine Nachricht ein und drücken Sie Enter: ");
        Scanner sc = new Scanner(System.in);

        sc.nextLine();
        stopAnnahme = true; // da Eingabe getätigt wurde, wird die Annahme von Nachrichten gestoppt
        try {
            sender1.join(); // auf Sender und Empf warten
            sender2.join();

            empfaenger1.join();
            empfaenger2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endtime = System.currentTimeMillis();
        System.out.println("Die Laufzeit beträgt " + (endtime - starttime) + "ms");

    }

    public static Set<Thread> getAllRunningThreads() {
        return Thread.getAllStackTraces().keySet();
    }

}

class A14NachrichtenPuffer {

    public int groesseNachrichtenPuffer;
    public A14Nachricht[] nachrichtenPuffer;
    public int lesePos = 0; // nötig für Ringspeicher
    public int schreibePos = 0; // nötig für Ringspeicher

    public boolean ersterSchreibevorgang = true;

    public A14NachrichtenPuffer(int groesse) {
        this.groesseNachrichtenPuffer = groesse;
        this.nachrichtenPuffer = new A14Nachricht[groesse];
    }

    // synchronized, da alle Threads auf gloable Variablen zugreifen....
    public synchronized void schreibePuffer(A14Nachricht nachricht) { // nötig für Ringspeicher/Nachrichtenpuffer
        if (nachrichtenPuffer[schreibePos] != null && !ersterSchreibevorgang) {
            try {
                wait(); //wartet bis Nachricht gelesen wurde und die Stelle frei zum Schreiben ist
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ersterSchreibevorgang = false;

        nachrichtenPuffer[schreibePos] = nachricht;
        //System.out.println("SP "+schreibePos);
        if (schreibePos < groesseNachrichtenPuffer - 1) {
            schreibePos++;
        } else {
            schreibePos = 0;
        }
        notify(); //beendet wait von Leser, da jetzt etwas neues gelesen werden kann
    }

    //    public synchronized ArrayList<Object> lesePuffer() { // nötig für Ringspeicher/Nachrichtenpuffer
    public synchronized boolean lesePuffer() { // nötig für Ringspeicher/Nachrichtenpuffer

        try {
            int countNachrichten = 0;
            for (int i = 0; i < nachrichtenPuffer.length; i++) {
                if (nachrichtenPuffer[i] != null)
                    countNachrichten++;
            }
            if (countNachrichten == 0 && !A14MsgSystem.stopAnnahme) //wenn Annahme noch nicht gestoppt und alle Nachrichten schon geholt wurden
                wait(); //warten bis Schreiber etwas in Puffer schreibt
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        A14Nachricht nachricht = nachrichtenPuffer[lesePos];
        System.out.println("Empfaenger-Thread " + ((EmpfaengerThread) Thread.currentThread()).empfaengerName + " holt Nachricht: " + nachricht
                + " aus NachrichtenPuffer...");

        nachrichtenPuffer[lesePos] = null; // nach lesen, Nachricht wieder löschen
        if (nachricht != null) { //nur lesePos erhöhen, falls Nachricht im Puffer steht
            if (lesePos < groesseNachrichtenPuffer - 1) {
                lesePos++;
            } else {
                lesePos = 0;
            }
        }

        //list speichert das StopFlag zum Zeitpunkt des Lesens der Nachricht
//        ArrayList<Object> list = new ArrayList<>();
//        list.add(nachrichtenPuffer[lesePos]); //zeigt, ob nächste Nachricht vorhanden ist oder nicht
//        list.add(A14MsgSystem.stopAnnahme);

        if (nachrichtenPuffer[lesePos] == null && A14MsgSystem.stopAnnahme) {
            A14MsgSystem.alleGelesen = true;
            for (Thread t : A14MsgSystem.getAllRunningThreads()) {
                try {
                    EmpfaengerThread et = (EmpfaengerThread) t;
                    et.interrupt();
                } catch (ClassCastException e){

                }
            }
        }

        notify(); //beendet wait von Schreiber, da jetzt etwas gelesen wurde, also neuer Platz zum Schreiben ist
        return A14MsgSystem.alleGelesen;
    }
}

class SenderThread extends Thread {

    public String senderName;
    public A14NachrichtenPuffer puffer;

    public SenderThread(String name, A14NachrichtenPuffer puffer) {
        this.senderName = name;
        this.puffer = puffer;
    }

    @Override
    public void run() {
        System.out.println("Sender-Thread " + senderName + " gestartet...");

        while (true) {
            if (A14MsgSystem.stopAnnahme)// Prüfung, ob Annahme durch Eingabe in Konsole gestoppt wurde
                break;
            int rndmZahl = getRandomNumber(0, 29);
            A14Nachricht nachricht = new A14Nachricht(senderName, rndmZahl); // Nachricht mit rdnm Zahl generieren
            try {
                puffer.schreibePuffer(nachricht); // neue Nachricht in NachrichtenPuffer speichern
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
    public A14NachrichtenPuffer puffer;

    public EmpfaengerThread(String name, A14NachrichtenPuffer puffer) {
        this.empfaengerName = name;
        this.puffer = puffer;
    }

    @Override
    public void run() {
        System.out.println("Empfaenger-Thread " + empfaengerName + " gestartet...");

        while (true) {
//            ArrayList<Object> nachrichtenListe = puffer.lesePuffer();
            //Liste: stelle 0 = nachricht , stelle 1 = StopAnnahme-Flag
            if (puffer.lesePuffer() || A14MsgSystem.alleGelesen) { // Prüfung, ob alle Nachrichten gelesen wurden
                for (Thread t : A14MsgSystem.getAllRunningThreads()) {
                    try {
                        EmpfaengerThread et = (EmpfaengerThread) t;
                        et.interrupt();
                    } catch (ClassCastException e){

                    }
                }
                break;
            }
            try {
                Date date = new Date();
                long t = date.getTime(); // aus Angabe gegeben....
                long dauer = t % 1000;
                if (dauer != 0) {
                    sleep(dauer); // holen und verarbeiten einer nachricht dauert eine gewisse Zeit
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            // Prüfung ob alle Nachrichten gelesen wurden und Annahme von Nachrichten
            // gestoppt wurde durch Eingabe in Konsole

//            if ((nachrichtenListe.get(0) == null && (boolean) nachrichtenListe.get(1) == true) || A14MsgSystem.alleGelesen) {    // Empfänger kann noch weiterlesen bis alle nachrichten == null sind ...
//                break; // aus while-Schleife springen
//            }
        }
        System.out.println("Empfaenger-Thread " + empfaengerName + " beendet...");
    }


    //TO-DO: Beim empfänger auf Null nachricht vor ausgabe prüfen

}