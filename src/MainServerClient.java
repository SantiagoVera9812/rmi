import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

public class MainServerClient {

    public static void main(String[] args) throws IOException{
        ServerClient serv = new ServerClient(); //Se crea el servidor

        System.out.println("Iniciando servidor\n");
        ArrayList<Integer> numeros = new ArrayList<>();
        numeros = serv.startServer();
        serv.operacionGeneral(numeros); //Se inicia el servidor
    }
}
