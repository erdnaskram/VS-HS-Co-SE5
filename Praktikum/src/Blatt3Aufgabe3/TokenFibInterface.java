package Blatt3Aufgabe3;

import java.rmi.Remote;

import Blatt3Aufgabe1.TokenInterface;
import Blatt3Aufgabe2.FibInterface;

public interface TokenFibInterface extends TokenInterface, FibInterface, Remote{

}
