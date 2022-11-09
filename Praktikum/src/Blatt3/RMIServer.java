package Blatt3; //rmilernen.tokenfibon

public class RMIServer implements RMIInterface{

    private static String servername;

    public static void main(String[] args) {
        servername = args[0];
    }

    @Override
    public char rmiServernameAt(int i) {
        return servername.charAt(i);
    }

    @Override
    public int rmiStrlen(String s) {
        if(s == null)
            return -1;
        return s.length();
    }

    @Override
    public String rmiFirstToken(String s, char c) {
        if(s.contains(String.valueOf(c)))
            return s.substring(0, s.indexOf(c));
        return s;
    }

    @Override
    public RAIBCResponse rmiRestAndIndexByChar(String s, char c) {
        if(s.contains(String.valueOf(c)))
            return new RAIBCResponse(s.substring(s.indexOf(c)+1), s.indexOf(c));
        return new RAIBCResponse("", -1);
    }

    @Override
    public String[] rmiSplitAt(String s, int i) {
        String[] response = new String[2];
        response[0] = s.substring(0, i);
        response[1] = s.substring(i, s.length());

        return new String[0];
    }

    @Override
    public void rmiIncrement() {

    }

    @Override
    public void rmiShutdown() {

    }

    @Override
    public char rmiServernameAtWithException(int i) throws Exception {
        return 0;
    }
}
