package Blatt1;
//package threadslernen;

public class A14Nachricht {

	private static int counter = 0; //mitlaufende ID
	private int id;
	private String nameSender;
	private int inhalt;
	
	
	public A14Nachricht (String name, int inhalt) {
		counter++;
		this.id = counter;
		this.nameSender=name;
		this.inhalt=inhalt;
		
	}

	
	


	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getNameSender() {
		return nameSender;
	}

	public void setNameSender(String nameSender) {
		this.nameSender = nameSender;
	}

	public int getInhalt() {
		return inhalt;
	}

	public void setInhalt(int inhalt) {
		this.inhalt = inhalt;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "A14Nachricht [id=" + id + ", nameSender=" + nameSender + ", inhalt=" + inhalt + "]";
	}
	
	

}
