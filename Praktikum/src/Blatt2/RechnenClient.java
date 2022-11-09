package Blatt2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RechnenClient {

	public static void main(String[] args) throws IOException {

		//ARGS: 127.0.0.1 8000

		System.out.println("Client gestartet...");

		try {
			System.out.println("Auf Verbindung warten...");

			String eingabeAdress = args[0];
			String[] adressStringArr = eingabeAdress.split("\\.");

			byte[] adress = new byte[4]; //Laurin im HS-Netzwerk = 127.0.0.1
			adress[0] = (byte) Integer.parseInt(adressStringArr[0]);
			adress[1] = (byte) Integer.parseInt(adressStringArr[1]);
			adress[2] = (byte) Integer.parseInt(adressStringArr[2]);
			adress[3] =  (byte) Integer.parseInt(adressStringArr[3]);

			//InetAddress localAdress = InetAddress.getLocalHost();

			InetAddress localAdress = InetAddress.getByAddress(adress);// adresse und port -->werden als args mitgegben werden!!!!

			//bsp port = 8000
			try (Socket clientSocket = new Socket(localAdress, Integer.parseInt(args[1]));
				 //zum antworten an Server
				 DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

				 //zum lesen der Nachricht von Server
				 DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
			) {

				System.out.println("Verbunden mit Server...");


				Scanner scanner = new Scanner(System.in);
				while (true) {
					System.out.println("Text eingeben: (quit für beenden oder enter für weiter)");
					String inputScanner = scanner.nextLine();
					if ("quit".equalsIgnoreCase(inputScanner)) { //bei quit beenden
						System.out.println("Wird beendet...");
						break;
					}
					System.out.println("Fktnr (1 sum up numbers, 2 count positive numbers, 5 close-connection\n"
							+ "(Close), 7 server-shutdown (Shutdown) ) eingeben:");
					short fktZahl = scanner.nextShort();
					scanner.skip("\\R");
					boolean closeFlag = false;
					if (fktZahl == 5 || fktZahl == 7) { //falls FktNr 5 oder 7 --> Keine Parameter Eingabe nötig...
						closeFlag = true;

					}

					System.out.println("Bitte Simulationszeit in ms eingeben (max.32000):");
					short warteZeit = scanner.nextShort();
					scanner.skip("\\R");

					short[] paramsArr;

					if (closeFlag != true) {
						System.out.println("Bitte Anzahl der Paramter eingeben:");
						short paramAnz = scanner.nextShort();
						scanner.skip("\\R");
						paramsArr = new short[paramAnz];
						//paramsArr mit der gewünschten Anzahl an Parametern füllen
						for (int i = 0; i < paramAnz; i++) {
							System.out.println("Parameter " + (i + 1) + ":");
							short param = scanner.nextShort();
							scanner.skip("\\R");
							paramsArr[i] = param;

						}
					} else { // FltNr 5 und 7 Handling: Texte "Close" und "ShutDown" in das paramsArr Char für Char schreiben
						String txt;
						if (fktZahl == 5) {
							txt = "Close";
						} else {
							txt = "ShutDown";
						}
						// Parameter mit den Buchstaben der Texte füllen
						char[] arrChar = txt.toCharArray();
						paramsArr = new short[arrChar.length];
						for (int i = 0; i < arrChar.length; i++) {
							paramsArr[i] = (short) arrChar[i];
						}

					}

					RechnenRequest request = new RechnenRequest();
					request.setMethodenID(fktZahl);
					request.setSt(warteZeit);
					request.setParams(paramsArr);

					// Client schreibt die von Nutzer festgelegten Parameter auf outputStream, damit
					// sie an Server geschickt werden...
					// Protokoll konform an Server schicken
					outputStream.writeShort(request.getMethodenID());
					outputStream.writeShort(request.getSt());
					short[] params = request.getParams();
					outputStream.writeShort(params.length);
					for (int i = 0; i < params.length; i++) {
						outputStream.writeShort(params[i]);

					}

					// von Server auf outputStream(für Client inputStream) geschriebenes Resultat auslesen
					if (fktZahl != 5 && fktZahl != 7) { //nur nötig wenn es eine Rechen-Fkt. ist
						int response = inputStream.readInt(); // inputStream einlesen --> Antwort von Server aus InputStream auslesen
						System.out.println("Von Server berechnetes Resultat: " + response);
					}
				}
				scanner.close();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
