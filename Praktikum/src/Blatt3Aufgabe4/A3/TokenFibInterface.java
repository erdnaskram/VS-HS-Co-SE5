package Blatt3Aufgabe4.A3;

import java.rmi.Remote;

import Blatt3Aufgabe4.A1.TokenInterface;
import Blatt3Aufgabe4.A2.FibInterface;

public interface TokenFibInterface extends TokenInterface, FibInterface, Remote{

}
