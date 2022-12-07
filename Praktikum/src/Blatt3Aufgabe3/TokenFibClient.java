package Blatt3Aufgabe3;

import java.rmi.Naming;
import java.util.Arrays;

public class TokenFibClient {

	public static void main(String[] args) {
		try {

			// Rmi-Reg Adresse festlegen
			String remoObjNameToken = "rmi://127.0.0.1/a3/token";

			// obj anfrangen aus Register (ro=externe Objektreferenz)
			TokenInterface rotoken = (TokenInterface) Naming.lookup(remoObjNameToken);
			// Fkt. des Servers nun für Client zur vefügung gestellt...

			String remoObjNameFib = "rmi://127.0.0.1/a3/fib";
			FibInterface rofib = (FibInterface) Naming.lookup(remoObjNameFib);
			
			String remoObjNameTokenFib = "rmi://127.0.0.1/a3/TokenFib";
			TokenFibInterface rotokenfib = (TokenFibInterface) Naming.lookup(remoObjNameTokenFib);

			
			if(args.length > 0){
				System.out.println("rmiShutdown für alle drei aufrufen");
				rotoken.rmiShutdown();
				rofib.rmiShutdown();
				rotokenfib.rmiShutdown();
				return;
			}

			// Fkt von Server aufrufen
			int responseStrlen = rotoken.rmiStrlen("TEST");
			System.out.println("token: responseStrlen: " + responseStrlen + " ID:"+rotoken.hashCode());

			rotoken.rmiIncrement();

			int responseServernameAt = rotoken.rmiServernameAt(5);
			System.out.println("token: responseServernameAt=: " + responseServernameAt + " ID:"+rotoken.hashCode());

			RAIBCResponse responseRestAndIndexByChar = rotoken.rmiRestAndIndexByChar("testZwei", 'Z');
			System.out.println("token: responseRestAndIndexByChar=: " + responseRestAndIndexByChar + " ID:"+rotoken.hashCode());

			String[] responseSplitAt = rotoken.rmiSplitAt("TestDrei", 5);
			System.out.println("token: responseSplitAt=: " + Arrays.toString(responseSplitAt) + " ID:"+rotoken.hashCode());

			int responseServernameAtWithException = rotoken.rmiServernameAtWithException(5);
			System.out.println("token: responseServernameAtWithException=: " + responseServernameAtWithException + " ID:"+rotoken.hashCode());

			long responseRmiFibon = rofib.rmiFibon(7);
			System.out.println("fib: responseRmiFibon: " + responseRmiFibon + " ID:"+rofib.hashCode());

			long[] responseRmiFibonArray = rofib.rmiFibonArray(5);
			System.out.println("fib: responseRmiFibonArray: " + Arrays.toString(responseRmiFibonArray) + " ID:"+rofib.hashCode());

			long[] array = new long[5];
			array[0] = 1;
			array[1] = 2;
			array[2] = 3;
			array[3] = 4;
			array[4] = 5;

			EinfachVerketteListe responseRmiConvertToList = rofib.rmiConvertToList(array);
			System.out.println("fib: responseRmiConvertToList: " + responseRmiConvertToList.toString() + " ID:"+rofib.hashCode());
			
			int responseStrlen2 = rotokenfib.rmiStrlen("TEST");
			System.out.println("token-fib: responseStrlen: " + responseStrlen2 + " ID:"+rotokenfib.hashCode());

			rotokenfib.rmiIncrement();

			int responseServernameAt2 = rotokenfib.rmiServernameAt(5);
			System.out.println("token-fib: responseServernameAt=: " + responseServernameAt2  + " ID:"+rotokenfib.hashCode());

			RAIBCResponse responseRestAndIndexByChar2 = rotokenfib.rmiRestAndIndexByChar("testZwei", 'Z');
			System.out.println("token-fib: responseRestAndIndexByChar=: " + responseRestAndIndexByChar2 + " ID:"+rotokenfib.hashCode());

			String[] responseSplitAt2 = rotokenfib.rmiSplitAt("TestDrei", 5);
			System.out.println("token-fib: responseSplitAt=: " + Arrays.toString(responseSplitAt2) + " ID:"+rotokenfib.hashCode());

			int responseServernameAtWithException2 = rotokenfib.rmiServernameAtWithException(5);
			System.out.println("token-fib: responseServernameAtWithException=: " + responseServernameAtWithException2 + " ID:"+rotokenfib.hashCode());

			long responseRmiFibon2 = rotokenfib.rmiFibon(7);
			System.out.println("token-fib: responseRmiFibon: " + responseRmiFibon2 + " ID:"+rotokenfib.hashCode());

			long[] responseRmiFibonArray2 = rotokenfib.rmiFibonArray(5);
			System.out.println("token-fib: responseRmiFibonArray: " + Arrays.toString(responseRmiFibonArray2) + " ID:"+rotokenfib.hashCode());

			long[] array2 = new long[5];
			array[0] = 1;
			array[1] = 2;
			array[2] = 3;
			array[3] = 4;
			array[4] = 5;

			EinfachVerketteListe responseRmiConvertToList2 = rotokenfib.rmiConvertToList(array2);
			System.out.println("token-fib: responseRmiConvertToList: " + responseRmiConvertToList2.toString() + " ID:"+rotokenfib.hashCode());

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

	}
}
