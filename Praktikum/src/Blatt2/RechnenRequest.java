package Blatt2;

public class RechnenRequest {

	short methodenID;
	short st;
	short[] params;

	public RechnenRequest() {
	}

	public short getMethodenID() {
		return methodenID;
	}

	public void setMethodenID(short methodenID) {
		this.methodenID = methodenID;
	}

	public short getSt() {
		return st;
	}

	public void setSt(short st) {
		this.st = st;
	}

	public short[] getParams() {
		return params;
	}

	public void setParams(short[] params) {
		this.params = params;
	}
	
	

}
