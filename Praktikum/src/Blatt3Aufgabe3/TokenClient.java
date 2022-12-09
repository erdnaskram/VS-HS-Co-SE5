package Blatt3Aufgabe3;

import java.rmi.Naming;
import java.util.Arrays;

public class TokenClient {

	public static void main(String[] args) {

		try {
			//TODO mit args geht noch nicht ...

			//"rmi://127.0.0.1/token" in ARGS mitgegben!!!!

			//Rmi-Reg Adresse festlegen
			String remoObjName = args[0];
			//String remoObjName = "rmi://127.0.0.1/token";

			//obj anfrangen aus Register (ro=externe Objektreferenz)
			TokenInterface ro = (TokenInterface) Naming.lookup(remoObjName);
			//Fkt. des Servers nun für Client zur vefügung gestellt...

			if (args.length > 1){
				System.out.println("rmiShutdown aufrufen");
				ro.rmiShutdown();
				return;
			}

			//Fkt von Server aufrufen
			int responseStrlen = ro.rmiStrlen("TEST");
			System.out.println("responseStrlen: " + responseStrlen);

			ro.rmiIncrement();

			int responseServernameAt = ro.rmiServernameAt(5);
			System.out.println("responseServernameAt=: " + responseServernameAt);

			RAIBCResponse responseRestAndIndexByChar = ro.rmiRestAndIndexByChar("testZwei", 'Z');
			System.out.println("responseRestAndIndexByChar=: " + responseRestAndIndexByChar);

			String[] responseSplitAt = ro.rmiSplitAt("TestDrei", 5);
			System.out.println("responseSplitAt=: " + Arrays.toString(responseSplitAt));

			int responseServernameAtWithException = ro.rmiServernameAtWithException(5);
			System.out.println("responseServernameAtWithException=: " + responseServernameAtWithException);


		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}


	}
}
