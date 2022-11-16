package Blatt3;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class TokenClient {
	
	 public static void main(String[] args) {

	        try {
	        	
	        	//"rmi://127.0.0.1/random/token" in ARGS mitgegben!!!!
	        	
	        	//Rmi-Reg Adresse festlegen
	        	String remoObjName = args[0];
	        	//String remoObjName = "rmi://127.0.0.1/token";
	        	
	        	//obj anfrangen aus Register (ro=externe Objektreferenz)
	        	TokenInterface ro  = (TokenInterface) Naming.lookup(remoObjName);
				//Fkt. des Servers nun für Client zur vefügung gestellt...
	        	
	        	//Fkt von Server aufrufen
	            int responseStrlen = ro.rmiStrlen("TEST");
	            System.out.println("responseStrlen: " + responseStrlen);
	            
	            ro.rmiIncrement();
	            
	            int responseServernameAt= ro.rmiServernameAt(5);
	            System.out.println("responseServernameAt=: " + responseServernameAt);
	            
	            RAIBCResponse responseRestAndIndexByChar= ro.rmiRestAndIndexByChar("testZwei", 'Z');
	            System.out.println("responseRestAndIndexByChar=: " + responseRestAndIndexByChar);
	            
	            String[] responseSplitAt= ro.rmiSplitAt("TestDrei", 5);
	            System.out.println("responseSplitAt=: " + Arrays.toString(responseSplitAt));
	            
	            int responseServernameAtWithException= ro.rmiServernameAtWithException(5);
	            System.out.println("responseServernameAtWithException=: " + responseServernameAtWithException);
	            
	            //TO-DO: statten Sie ihren Client mit einer Aufrufoperation aus, mit der es möglich ist, den Server zu beenden
	            
	            
	        } catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }


	 }
}
