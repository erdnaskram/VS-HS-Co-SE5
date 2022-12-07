package Blatt1;
//package threadslernen;

import java.util.*;

public class A14MsgSystem {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long endTime;
        int groesseNachrichtenPuffer = Integer.parseInt(args[0]);

        A14NachrichtenPuffer nachrichtenPuffer = new A14NachrichtenPuffer(groesseNachrichtenPuffer); // Puffer für Nachrichten

        Thread sender1 = new SenderThread("Sender 1", nachrichtenPuffer);
        Thread sender2 = new SenderThread("Sender 2", nachrichtenPuffer);

        Thread empfaenger1 = new EmpfaengerThread("Empfaenger 1", nachrichtenPuffer);
        Thread empfaenger2 = new EmpfaengerThread("Empfaenger 2", nachrichtenPuffer);

        sender1.start();
        empfaenger1.start();
        sender2.start();
        empfaenger2.start();

        // Auf Eingabe warten
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        nachrichtenPuffer.stop();
        try {
            sender1.join();
            sender2.join();
            empfaenger1.join();
            empfaenger2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Die Laufzeit beträgt " + (endTime - startTime) + "ms");
    }
}

class A14NachrichtenPuffer {
    public boolean stopped = false;
    public int groesseNachrichtenPuffer;
    public A14Nachricht[] nachrichtenPuffer;
    public int lesePos = 0;
    public int schreibePos = 0;

    public A14NachrichtenPuffer(int groesse) {
        this.groesseNachrichtenPuffer = groesse;
        this.nachrichtenPuffer = new A14Nachricht[groesse];
    }

    public void stop() {
        stopped = true;
    }

    public boolean isRunning() {
        return !stopped;
    }

    public boolean isEmpty() {
        return nachrichtenPuffer[lesePos] == null;
    }

    public boolean isFull() {
        return nachrichtenPuffer[schreibePos] != null;
    }

    public synchronized void schreibePuffer(A14Nachricht nachricht) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        nachrichtenPuffer[schreibePos] = nachricht;
        if (schreibePos < groesseNachrichtenPuffer - 1) {
            schreibePos++;
        } else {
            schreibePos = 0;
        }
        notifyAll();
    }

    public synchronized A14Nachricht lesePuffer() {
        while (isEmpty()) {
            try {
                wait();
                // ToDo: Wenn gestoppt + nur ein Inhalt aber zwei wartende Leser => abfangen!
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        A14Nachricht nachricht = nachrichtenPuffer[lesePos];
        nachrichtenPuffer[lesePos] = null; // nach lesen, Nachricht wieder löschen
        if (lesePos < groesseNachrichtenPuffer - 1) {
            lesePos++;
        } else {
            lesePos = 0;
        }
        notifyAll();
        return nachricht;
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

        while (puffer.isRunning()) {
            int randomZahl = getRandomNumber(0, 29);
            A14Nachricht nachricht = new A14Nachricht(senderName, randomZahl);
            try {
                System.out.println("Sender-Thread " + senderName + " erzeugt Nachricht: " + nachricht
                        + " und speichert sie im NachrichtenPuffer...");
                if (randomZahl > 0) {
                    //noinspection BusyWait
                    sleep((long) randomZahl * randomZahl); // Von Prof gewünscht!
                }
                puffer.schreibePuffer(nachricht);
            } catch (InterruptedException e) {
                break;
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

        while (puffer.isRunning() || !puffer.isEmpty()) {
            A14Nachricht nachricht = puffer.lesePuffer();

            try {
                Date date = new Date();
                long t = date.getTime();
                long dauer = t % 1000;
                if (dauer > 0) {
                    //noinspection BusyWait
                    sleep(dauer); // Gewollt von Prof!
                }

                System.out.println("Empfaenger-Thread " + empfaengerName + " holt Nachricht: " + nachricht
                        + " aus NachrichtenPuffer...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Empfaenger-Thread " + empfaengerName + " beendet...");
    }
}