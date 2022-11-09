package Blatt3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public char rmiServernameAt(int i);
    public int rmiStrlen(String s);
    public String rmiFirstToken(String s, char c);
    public RAIBCResponse rmiRestAndIndexByChar(String s, char c);
    public String[] rmiSplitAt(String s, int i);
    public void rmiIncrement(); //incrementiert long-Wert
    public void rmiShutdown();
    public char rmiServernameAtWithException(int i) throws Exception;

}
