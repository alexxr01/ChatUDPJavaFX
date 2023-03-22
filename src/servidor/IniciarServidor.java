package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;

// Aquí es donde se inicia el servidor, con un máximo de 10 threads, es decir 10 cliente máximos
public class IniciarServidor implements Runnable {
	Socket socket = null; // Para comunicar que cliente ha accedido al servidor.
	Usuario user = new Usuario(); // Para manejar los usuarios que hay en el servidor conectados.
	ServerSocket server_socket = null; // Se usará para aceptar un cliente
	// Se crea una instanacia de Thread.
	Thread hilo = new Thread();

	// Acción a realizar por el hilo.
	@Override
	public void run() {
		try {
			// Instancia donde crearemos el servidor y el puerto.
			server_socket = new ServerSocket(3000);
			while (true) {
				socket = server_socket.accept(); // Esperamos las conexiones y las aceptamos			
				// Creamos un nuevo hilo, de la clase "ServicioChat" asignandole el usuario y el socket.
				hilo = new Thread(new ServicioChat(user, socket));
				hilo.start(); // Lo iniciamos.
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
