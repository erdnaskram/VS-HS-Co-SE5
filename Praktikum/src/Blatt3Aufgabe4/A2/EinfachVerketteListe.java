package Blatt3Aufgabe4.A2;

import java.io.Serializable;

//Serializabel, da iwr zwischen Server und CLient hin und her schicken
public class EinfachVerketteListe implements Serializable {

	ListElement startElem = new ListElement(-1);

	public EinfachVerketteListe() {}

	public void addLast(ListElement o) {
		ListElement newElem = new ListElement(o.wert);
		ListElement lastElem = getLastElem();
		lastElem.setNextElem(newElem);
	}

	public void insertAfter(ListElement prevItem, ListElement newItem) {
		ListElement newElem, nextElem, pointerElem;
		pointerElem = startElem.getNextElem();
		while (pointerElem != null && !pointerElem.getObj().equals(prevItem)) {
			pointerElem = pointerElem.getNextElem();
		}
		newElem = new ListElement(newItem.wert);
		nextElem = pointerElem.getNextElem();
		pointerElem.setNextElem(newItem);
		newElem.setNextElem(nextElem);
	}

	public void delete(Object o) {
		ListElement le = startElem;
		while (le.getNextElem() != null && !le.getObj().equals(o)) {
			if (le.getNextElem().getObj().equals(o)) {
				if (le.getNextElem().getNextElem() != null)
					le.setNextElem(le.getNextElem().getNextElem());
				else {
					le.setNextElem(null);
					break;
				}
			}
			le = le.getNextElem();
		}
	}

	public boolean find(Object o) {
		ListElement le = startElem;
		while (le != null) {
			if (le.getObj().equals(o))
				return true;
			le = le.nextElem;
		}
		return false;
	}

	public ListElement getFirstElem() {
		return startElem;
	}

	public ListElement getLastElem() {
		ListElement le = startElem;
		while (le.getNextElem() != null) {
			le = le.getNextElem();
		}
		return le;
	}

	public void writeList() {
		ListElement le = startElem;
		while (le != null) {
			System.out.println(le.getObj());
			le = le.getNextElem();
		}
	}

	@Override
	public String toString() {
		return "EinfachVerketteListe{" +
				"startElem=" + startElem +
				'}';
	}
}
