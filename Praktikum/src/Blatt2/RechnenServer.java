package Blatt2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RechnenServer {

	public static void main(String[] args) {

		System.out.println("Server gestartet...");
		try {
			ServerSocket server = new ServerSocket(8000);
			int idCounter = 0;
			while (true) {
				idCounter++;
				System.out.println("Warte auf Verbindungsanfrage...");
				// clientSocket steht für die Verbindung von Server zu Client
				Socket clientSocket = server.accept(); // When a request is received, the accept method will return a Socket class instance, which represents the connection between that client and the server.
				System.out.println("Threadnummer:"+idCounter+"    Verbunden mit Client ...");
				WorkerThread worker = new WorkerThread(clientSocket,idCounter);
				worker.start();
//				while (true) {
//						//hier war alles was nun in der run() ist...
//				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class WorkerThread extends Thread {
	int id ;
	Socket clientSocket = null;

	public WorkerThread(Socket clientSocket, int id) {
		this.id = id;
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			// zum lesen der Nachricht von Client
			DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
			// zum antworten an Client
			DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());


			// von Client auf outputStream(für Server dann inputStream) geschriebene Daten lesen
			// Protokoll konform lesen
			System.out.println("Lesen der Params etc.... " + "Threadnummer: "+this.id);
			short methodenID = inputStream.readShort();
			short st = inputStream.readShort();
			short paramsLength = inputStream.readShort();
			short[] params = new short[paramsLength];
			for (int i = 0; i < paramsLength; i++) {
				params[i] = inputStream.readShort();
			}
			System.out.println("Resultat senden... " + "Threadnummer: "+this.id);
			int result = 0;

			// Fachlogik --> mID legt fest welche Rechenoperation ausgeführt werden soll
			switch (methodenID) {
				case 1: {
					// Summe bilden
					Thread.sleep(st); // geüwnschte Verarbeitungszeit abwarten
					for (short s : params) {
						result += s;
					}
					// Resultat auf outputStream schreiben und so für client in seinem inputStream
					// zur verfügung stellen
					outputStream.writeInt(result);
					break;

				}
				case 2: {
					// pos zahlen zählen
					Thread.sleep(st); // gewünschte Verarbeitungszeit abwarten
					for (short s : params) {
						if (s >= 0) {
							result++;
						}
					}
					// Resultat auf outputStream schreiben und so für client in seinem inputStream
					// zur verfügung stellen
					outputStream.writeInt(result);
					break;
				}
				case 5: {
					Thread.sleep(st);// gewünschte Verarbeitungszeit abwarten
					clientSocket.close();// Verbindung schließen
					String closeTxt = "";
					for (short s : params) { // Ausgabetext = Close --> wurde von Client in das Param Array geschrieben
						closeTxt += (char) s; // einzelnen Chars zu String zusammenbringen
					}
					System.out.println("Empfangen: Text=" + closeTxt);
					break;

				}
				case 7: {
					Thread.sleep(st); // gewünschte Verarbeitungszeit abwarten
					// Server herunterfahren
					// server.close();
					String shutdownTxt = "";
					for (short s : params) { // Ausgabetext = ShutDown --> wurde von Client in das Param Array geschrieben
						shutdownTxt += (char) s; // einzelnen Chars zu String zusammenbringen
					}
					System.out.println("Empfangen: Text=" + shutdownTxt);
					break;

				}

				default:
					throw new IllegalArgumentException("Unexpected value: " + methodenID);
			}

		} catch (Exception e) {
		}

	}
}
