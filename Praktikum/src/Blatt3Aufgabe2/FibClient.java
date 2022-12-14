package Blatt3Aufgabe2;

import java.rmi.Naming;
import java.util.Arrays;


public class FibClient {

	public static void main(String[] args) {
		
		
		//in Aufruf-Parametern Zahl für Fibon() mitgeben!!!
		
		try {
        	//Rmi-Reg Adresse festlegen
            String remoObjName = args[0];
        	//String remoObjName = "rmi://127.0.0.1/fib";
        	
        	//obj anfrangen aus Register (ro=externe Objektreferenz)
        	FibInterface ro  = (FibInterface) Naming.lookup(remoObjName);


            if(args.length > 2){
                System.out.println("rmiShutdown aufrufen");
                ro.rmiShutdown();
                return;
            }

        	//Fkt von Server aufrufen
            long responseRmiFibon = ro.rmiFibon(Integer.parseInt(args[1]));
            System.out.println("responseRmiFibon: " + responseRmiFibon);

            long[] responseRmiFibonArray = ro.rmiFibonArray(5);
            System.out.println("responseRmiFibonArray: " + Arrays.toString(responseRmiFibonArray));
            
            long[] array = new long[5];
            array[0]=1;
            array[1]=2;
            array[2]=3;
            array[3]=4;
            array[4]=5;
           
            EinfachVerketteListe responseRmiConvertToList = ro.rmiConvertToList(array);
            System.out.println("responseRmiConvertToList: " + responseRmiConvertToList.toString());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


 }
	}


