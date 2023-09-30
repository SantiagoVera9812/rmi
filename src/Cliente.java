import java.io.*;
import java.net.ConnectException;
import java.util.Random;


public class Cliente extends Conexion
{     

    
    public Cliente() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        Random random = new Random();
        try
        {
            //Flujo de datos hacia el servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);

            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            //Se enviarán dos mensajes
            for (int i = 0; i < 4; i++)
            {
                int aleatorio = random.nextInt(1000 - 0 +1) + 0; // Número de 0 a 1000
                //Se escribe en el servidor usando su flujo de datos
                out.println(aleatorio + " ");
                System.out.println("Número aleatorio " + Integer.valueOf(i+1) + ": " + aleatorio);
            }

            out.println("FIN");

            String resultado = in.readLine();

            System.out.println("Resultado de la suma: " + resultado);

            cs.close();//Fin de la conexión
        }
        catch (ConnectException e2){
            System.out.println("No hay un servidor disponible");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}