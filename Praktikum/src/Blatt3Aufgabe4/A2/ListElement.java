package Blatt3Aufgabe4.A2;

import java.io.Serializable;

//Serializabel, da iwr zwischen Server und CLient hin und her schicken
public class ListElement implements Serializable {
	
	long wert; 
	public static int counter= 0;
	int index;
    ListElement nextElem; 

    public ListElement(long wert) { 
        this.wert = wert; 
        this.nextElem=null;
        this.index=counter;
        counter++;
    }

	public ListElement getNextElem() {
		return nextElem;
	}

	public void setNextElem(ListElement nextElem) {
		this.nextElem = nextElem;
	}

	public ListElement getObj() {
		return this;
	}

	@Override
	public String toString() {
		return "ListElement{" +
				"wert=" + wert +
				", index=" + index +
				", nextElem=" + nextElem +
				'}';
	}
}
