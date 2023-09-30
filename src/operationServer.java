import java.rmi.Remote;

public interface operationServer extends Remote {
    public int operacion(int a, int b) throws java.rmi.RemoteException;
}