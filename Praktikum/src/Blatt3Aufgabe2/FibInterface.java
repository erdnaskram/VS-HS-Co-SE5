package Blatt3Aufgabe2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FibInterface extends Remote {
	
	public long rmiFibon(int i) throws RemoteException;
	public long[] rmiFibonArray(int i) throws RemoteException;
	public EinfachVerketteListe rmiConvertToList(long[] array) throws RemoteException;
	public void rmiShutdown() throws RemoteException;

}
