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
			//clientSocket steht für die Verbindung von Server zu Client
			Socket clientSocket = server.accept(); //When a request is received, the accept method will return a Socket class instance, which represents the connection between that client and the server.
			System.out.println("Verbunden mit Client...");
			DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream()); //zum lesen der Nachricht von Client
			DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());  //zum antworten an Client	
			
			//von Client auf outputStream(für Server dann inputStream) geschriebene Daten lesen 
			//Protokoll konform lesen
			short methodenID = inputStream.readShort();
			short st = inputStream.readShort();
			short paramsLength = inputStream.readShort();
			short[] params = new short[paramsLength];
			for (int i = 0; i < paramsLength; i++) {
				params[i]=inputStream.readShort();
			}
			int result=0;
			
			
			//Fachlogik --> mID legt fest welche Rechenoperation ausgeführt werden soll
			switch (methodenID) {
			case 1: { 
				//Summe bilden
				try {
					Thread.sleep(st); //geüwnschte Verarbeitungszeit abwarten
					for (short s : params) {
						result += s;
					}
					//Resultat auf outputStream schreiben und so für client in seinem inputStream zur verfügung stellen
					outputStream.writeInt(result);
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			case 2:{
				//pos zahlen zählen
				try {
					Thread.sleep(st); //gewünschte Verarbeitungszeit abwarten
					for (short s : params) {
						if(s>=0) {
							result++;
						}
					}
					//Resultat auf outputStream schreiben und so für client in seinem inputStream zur verfügung stellen
					outputStream.writeInt(result);
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			case 5:{
				try {
					Thread.sleep(st);//gewünschte Verarbeitungszeit abwarten
					clientSocket.close();//Verbindung schließen
					String closeTxt = "";
					for (short s : params) { //Ausgabetext = Close --> wurde von Client in das Param Array geschrieben
						closeTxt+=(char)s;  //einzelnen Chars zu String zusammenbringen
					}
					System.out.println("Empfangen: Text="+closeTxt);
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				
			}
			case 7:{
				try {
					Thread.sleep(st); //gewünschte Verarbeitungszeit abwarten
					//Server herunterfahren
					server.close();
					String shutdownTxt = "";
					for (short s : params) { //Ausgabetext = ShutDown --> wurde von Client in das Param Array geschrieben
						shutdownTxt+=(char)s; //einzelnen Chars zu String zusammenbringen
					}
					System.out.println("Empfangen: Text="+shutdownTxt);
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + methodenID);
			}
			
			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
