package
        Blatt3Aufgabe4.A1;


import java.io.Serializable;

//Serializabel, da iwr zwischen Server und CLient hin und her schicken
public class RAIBCResponse implements Serializable {

    private String s;
    private int i;

    public RAIBCResponse(String s, int i){
        this.s = s;
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public int getI() {
        return i;
    }

    @Override
    public String toString() {
        return "RAIBCResponse{" +
                "s='" + s + '\'' +
                ", i=" + i +
                '}';
    }
}
