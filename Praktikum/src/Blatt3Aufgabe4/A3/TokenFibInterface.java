package Blatt3Aufgabe4.A3;

import java.rmi.Remote;

import A1.TokenInterface;
import A2.FibInterface;

public interface TokenFibInterface extends TokenInterface, FibInterface, Remote{

}
