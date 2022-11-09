package Blatt3; //rmilernen.tokenfibon

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer implements RMIInterface{

    private static String servername;
    private long longIncrement = 0;

    public static void main(String[] args) {
        servername = args[0];
        RMIServer server = new RMIServer();

        try {
            RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(server, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("RMIInterface", stub);
        } catch (AlreadyBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char rmiServernameAt(int i) {
        return servername.charAt(i);
    }

    @Override
    public int rmiStrlen(String s) {
        if(s == null)
            return -1;
        return s.length();
    }

    @Override
    public String rmiFirstToken(String s, char c) {
        if(s.contains(String.valueOf(c)))
            return s.substring(0, s.indexOf(c));
        return s;
    }

    @Override
    public RAIBCResponse rmiRestAndIndexByChar(String s, char c) {
        if(s.contains(String.valueOf(c)))
            return new RAIBCResponse(s.substring(s.indexOf(c)+1), s.indexOf(c));
        return new RAIBCResponse("", -1);
    }

    @Override
    public String[] rmiSplitAt(String s, int i) {
        String[] response = new String[2];

        if(i<0){
            response[0] = "";
            response[1] = s;
        }else if(i >=s.length()){
            response[0] = s;
            response[1] = "";
        }else {
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
    public void rmiShutdown() {
        //TODO
    }

    @Override
    public char rmiServernameAtWithException(int i) throws Exception {
        if(i >= servername.length() || i <0)
            throw new Exception();
        return servername.charAt(i);
    }
}
