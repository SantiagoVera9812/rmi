// ************************************************************
// Cliente.
// Pide la ejecución del método getTime,
// del objeto TimeServer.
// Para el cliente una vez cargado el objeto, lo trata com local
// ************************************************************

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

public class ServerClient extends Conexion{

    public ServerClient() throws IOException{super("servidor");}

    public ArrayList<Integer> startServer() throws IOException//Método para iniciar el servidor
    {
        System.out.println("Esperando..."); //Esperando conexión

        cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente

        System.out.println("Cliente en línea");

        //Se obtiene el flujo de salida del cliente para enviarle mensajes

        //Se le envía un mensaje al cliente usando su flujo de salida
        //Se obtiene el flujo entrante desde el cliente
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
        out.println("Petición recibida y aceptada");
        String mensajeCliente = "";
        String datos="";

        while ((mensajeCliente = entrada.readLine()) != null) {
            if (mensajeCliente.equals("FIN")) {
                break;
            }
            datos += mensajeCliente;
        }

        System.out.println("El mensaje del cliente: " + datos);

        int num1, num2, num3, num4;
        ArrayList<Integer> numeros = new ArrayList<>();

        String [] parts = datos.split(" ");
        num1 = Integer.valueOf(parts[0]);
        num2 = Integer.valueOf(parts[1]);

        num3 = Integer.valueOf(parts[2]);
        num4 = Integer.valueOf(parts[3]);
        numeros.add(num1);
        numeros.add(num2);
        numeros.add(num3);
        numeros.add(num4);
        return numeros;
    }

    public void operacionGeneral(ArrayList<Integer> numeros) throws IOException {
        PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
        // Variable que recibirá la hora del servidor
        String time = null;
        Random random = new Random();
        int num1, num2, res;

        try {
            // conectarse al servidor y cargar registro de objetos RMI
            // usar el método getTime del objeto conectado.

            res = conectarServOp1(numeros.get(0), numeros.get(1)) + conectarServOp2(numeros.get(2), numeros.get(3));
            System.out.println("La respuesta es: " + res);
            out.println(res);
        }
        catch (NotBoundException e) {
            System.out.println("Operation Server no se encontró en el registro");
            System.exit(0);
        }
        catch (RemoteException e) {
            System.out.println("Time error: " + e);
            System.exit(0);
        }
    }


    public int conectarServOp1(int num1, int num2) throws IOException, NotBoundException {
        int response = 0;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            operationServer TS = (operationServer)registry.lookup("operationServer");
            response = TS.operacion(num1, num2);
        } catch (NotBoundException e2) {
            return conectarServOp2(num1, num2);
        } catch (RemoteException e){
            return conectarServOp2(num1, num2);
            // Aquí puedes manejar el caso de timeout, por ejemplo, intentar nuevamente o mostrar un mensaje de error.
        }
        return (response);
    }

    public int conectarServOp2(int num1, int num2) throws IOException, NotBoundException {
        int response = 0;
        try {
            Registry registry2 = LocateRegistry.getRegistry("localhost", 1098);
            operationServer TS2 = (operationServer)registry2.lookup("operationServer");
            response = TS2.operacion(num1, num2);
        } catch (NotBoundException e2) {
            return conectarServOp1(num1, num2);
        } catch (RemoteException e){
            return conectarServOp1(num1, num2);
            // Aquí puedes manejar el caso de timeout, por ejemplo, intentar nuevamente o mostrar un mensaje de error.
        }
        return (response);
    }
}