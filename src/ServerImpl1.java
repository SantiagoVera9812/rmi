import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl1 extends UnicastRemoteObject implements operationServer {
    protected ServerImpl1() throws RemoteException {
        System.out.println("Arrancando Servidor de operación");
    }
    public int operacion(int a, int b) throws RemoteException {
        return a+b;
    }

    // Arranque del Servidor de Hora

    public static void main(String[] args) {
        try {

            // Crear el objeto cuyos métodos el cliente podrá usar
            ServerImpl1 TSI1 = new ServerImpl1();

            // Incluir el objeto en el registro del RMI en el puerto 1099,
            // para que el cliente lo pueda encontrar.
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("operationServer", TSI1);
            System.out.println("Objeto -OperationServer- Registrado en el RMI");

        } catch (RemoteException e) {
            System.out.println("Error: " + e);
        }
    }
}
