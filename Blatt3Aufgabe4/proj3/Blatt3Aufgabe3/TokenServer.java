package Blatt3Aufgabe3; //rmilernen.tokenfibon !!!

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// cd /Users/laurin/IdeaProjects/VS-HS-Co-SE5/out/production/VS-HS-Co-SE5
//rmiregistry in terminal!!!

@SuppressWarnings("serial")
public class TokenServer extends UnicastRemoteObject implements TokenInterface {
	

    private static String servername;
    private long longIncrement = 0;
	
	public TokenServer() throws RemoteException{
		super();
	}

    @Override
    public char rmiServernameAt(int i) throws RemoteException {
        return servername.charAt(i);
    }

    @Override
    public int rmiStrlen(String s) throws RemoteException{
        if(s == null)
            return -1;
        return s.length();
    }

    @Override
    public String rmiFirstToken(String s, char c) throws RemoteException{
        if(s.contains(String.valueOf(c)))
            return s.substring(0, s.indexOf(c));
        return s;
    }

    @Override
    public RAIBCResponse rmiRestAndIndexByChar(String s, char c) throws RemoteException{
        if(s.contains(String.valueOf(c)))
            return new RAIBCResponse(s.substring(s.indexOf(c)+1), s.indexOf(c));
        return new RAIBCResponse("", -1);
    }

    @Override
    public String[] rmiSplitAt(String s, int i) throws RemoteException{
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
    public void rmiShutdown() throws RemoteException{
        try {
        	
        	//Löschen aller Remote-Objekte des RMI-Servers aus der RMI-Registry mit Naming.unbind().
			Naming.unbind("rmi://127.0.0.1/token");
			
			
			//Löschen aller Remote-Objekts des RMI-Servers aus der RMI-Runtime mit UnicastRemoteObject.unexportObject().
			UnicastRemoteObject.unexportObject(this, false);
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }

    @Override
    public char rmiServernameAtWithException(int i) throws RemoteException{
        if(i >= servername.length() || i <0)
            throw new RemoteException();
        return servername.charAt(i);
    }
    
    
    public static void main(String[] args) throws MalformedURLException {
        servername = args[0];

        try {
        	
        	//create remote obj
        	TokenServer server = new TokenServer();
        	String remobjname = "rmi://127.0.0.1/token"; //Rmi Reg-Adresse festlegen
        	
        	//register remote obj
        	Naming.rebind(remobjname, server);//Registry mit Server verbinden bzw. hinterlegen
        	System.out.println("Remote Objekt registriert...");
           
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setServername(String servername) {
        TokenServer.servername = servername;
    }
}
