package Blatt3Aufgabe4.A1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TokenInterface extends Remote {

	public char rmiServernameAt(int i) throws RemoteException;

	public int rmiStrlen(String s) throws RemoteException;

	public String rmiFirstToken(String s, char c) throws RemoteException;

	public RAIBCResponse rmiRestAndIndexByChar(String s, char c) throws RemoteException;

	public String[] rmiSplitAt(String s, int i) throws RemoteException;

	public void rmiIncrement() throws RemoteException; // incrementiert long-Wert

	public void rmiShutdown() throws RemoteException;

	public char rmiServernameAtWithException(int i) throws Exception;

}
