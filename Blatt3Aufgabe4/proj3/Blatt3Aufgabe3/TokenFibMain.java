package Blatt3Aufgabe3;

import java.rmi.Naming;

public class TokenFibMain {
	
	//cd /Users/laurin/eclipse/Workspace/VS/src/Blatt3/out/production/Blatt3
	//rmiregistry in terminal!!!

	public static void main(String[] args) throws Exception {

		try {

			// create remote obj
			FibServer serverFib = new FibServer();
			String remobjnameFib = "rmi://127.0.0.1/a3/fib"; // Rmi Reg-Adresse festlegen
			// register remote obj
			Naming.rebind(remobjnameFib, serverFib);
			System.out.println("Fib-Server registriert...");

			TokenServer serverToken = new TokenServer();
			TokenServer.setServername("TokenServer");
			String remobjnameToken = "rmi://127.0.0.1/a3/token";
			Naming.rebind(remobjnameToken, serverToken);
			System.out.println("Token-Server registriert...");

			TokenFibServer serverTokenFib = new TokenFibServer();
			TokenFibServer.setServername("TokenFibServer");
			String remobjnameTokenFib = "rmi://127.0.0.1/a3/TokenFib";
			Naming.rebind(remobjnameTokenFib, serverTokenFib);
			System.out.println("TokenFib-Server registriert...");
			

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

}
