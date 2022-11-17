package Blatt3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//cd /Users/laurin/IdeaProjects/VS-HS-Co-SE5/out/production/VS-HS-Co-SE5
//rmiregistry in terminal!!!
public class TokenFibServer extends UnicastRemoteObject implements TokenFibInterface {

	private static String servername;
	private long longIncrement = 0;

	protected TokenFibServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public long rmiFibon(int i) throws RemoteException {

		long[] fibReihe = new long[i + 1];
		fibReihe[0] = 0;
		fibReihe[1] = 1;
		for (int j = 2; j <= i; j++) {
			fibReihe[j] = fibReihe[j - 1] + fibReihe[j - 2];
		}
		if (i < 0) {
			return 0;
		} else {
			return fibReihe[i];
		}
	}

	@Override
	public long[] rmiFibonArray(int i) throws RemoteException {
		long[] fibReihe = new long[i + 1];
		fibReihe[0] = 0;
		fibReihe[1] = 1;
		for (int j = 2; j <= i; j++) {
			fibReihe[j] = fibReihe[j - 1] + fibReihe[j - 2];
		}

		if (i < 0) {
			return new long[0];
		} else {
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
	public char rmiServernameAt(int i) throws RemoteException {
		return servername.charAt(i);
	}

	@Override
	public int rmiStrlen(String s) throws RemoteException {
		if (s == null)
			return -1;
		return s.length();
	}

	@Override
	public String rmiFirstToken(String s, char c) throws RemoteException {
		if (s.contains(String.valueOf(c)))
			return s.substring(0, s.indexOf(c));
		return s;
	}

	@Override
	public RAIBCResponse rmiRestAndIndexByChar(String s, char c) throws RemoteException {
		if (s.contains(String.valueOf(c)))
			return new RAIBCResponse(s.substring(s.indexOf(c) + 1), s.indexOf(c));
		return new RAIBCResponse("", -1);
	}

	@Override
	public String[] rmiSplitAt(String s, int i) throws RemoteException {
		String[] response = new String[2];

		if (i < 0) {
			response[0] = "";
			response[1] = s;
		} else if (i >= s.length()) {
			response[0] = s;
			response[1] = "";
		} else {
			response[0] = s.substring(0, i);
			response[1] = s.substring(i, s.length());
		}
		return response;
	}

	@Override
	public void rmiIncrement() {
		longIncrement++;
	}

	@Override
	public void rmiShutdown() throws RemoteException {
		try {

			// Löschen aller Remote-Objekte des RMI-Servers aus der RMI-Registry mit
			// Naming.unbind().
			Naming.unbind("rmi://127.0.0.1/tokenFib");

			// Löschen aller Remote-Objekts des RMI-Servers aus der RMI-Runtime mit
			// UnicastRemoteObject.unexportObject().
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

	@Override
	public char rmiServernameAtWithException(int i) throws RemoteException {
		if (i >= servername.length() || i < 0)
			throw new RemoteException();
		return servername.charAt(i);
	}
	

	public static void setServername(String servername) {
		TokenFibServer.servername = servername;
	}
}
