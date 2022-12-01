package Blatt3Aufgabe2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//cd /Users/laurin/IdeaProjects/VS-HS-Co-SE5/out/production/VS-HS-Co-SE5
//rmiregistry in terminal!!!

public class FibServer extends UnicastRemoteObject implements FibInterface {
	
	

	public FibServer() throws RemoteException {
		super();
	    
	}

	@Override
	public long rmiFibon(int i) throws RemoteException {
		
		long[] fibReihe = new long[i+1];
		fibReihe[0] = 0;
	    fibReihe[1] = 1;
	    for (int j = 2; j <= i; j++) {
	    	fibReihe[j] = fibReihe[j - 1] + fibReihe[j - 2];
	    }
		if(i < 0) {
			return 0;
		}
		else{
			return fibReihe[i];
		}
	}

	@Override
	public long[] rmiFibonArray(int i) throws RemoteException {
		long[] fibReihe = new long[i+1];
		fibReihe[0] = 0;
	    fibReihe[1] = 1;
	    for (int j = 2; j <= i; j++) {
	    	fibReihe[j] = fibReihe[j - 1] + fibReihe[j - 2];
	    }
		
		if(i < 0) {
			return new long[0];
		}
		else{
			return fibReihe;
		}
	}

	@Override
	public EinfachVerketteListe rmiConvertToList(long[] array) throws RemoteException {
		EinfachVerketteListe ausgabeList = new EinfachVerketteListe();
		
		for (int i = 0; i < array.length; i++) {
			ausgabeList.addLast(new ListElement(array[i]));
		}
		
	
		return ausgabeList;
		
	}
	
	@Override
	public void rmiShutdown() throws RemoteException {
		try {
        	
        	//Löschen aller Remote-Objekte des RMI-Servers aus der RMI-Registry mit Naming.unbind().
			Naming.unbind("rmi://127.0.0.1/fib");
			
			
			//Löschen aller Remote-Objekts des RMI-Servers aus der RMI-Runtime mit UnicastRemoteObject.unexportObject().
			UnicastRemoteObject.unexportObject(this, false);
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		
		try {

			// create remote obj
			FibServer server = new FibServer();
			String remobjname = "rmi://127.0.0.1/fib"; // Rmi Reg-Adresse festlegen

			// register remote obj
			Naming.rebind(remobjname, server);
			System.out.println("Remote Objekt registriert...");

		} catch (RemoteException | MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	

}
