package servidor;

import java.io.DataInputStream;
import java.net.Socket;

// Clase "ServicioChat" que principalmente se encargará de administrar
// y leer los mensajes de los clientes.

public class ServicioChat implements Runnable {

	Socket socket; // Necesario para la comunicación.
	DataInputStream in; // Necesario para recoger los datos.
	String nombre;
	Usuario usuario = new Usuario();

	// Constructor de la clase
	public ServicioChat(Usuario user, Socket socket) throws Exception {
		this.usuario = user;
		this.socket = socket;

		// Almacenamos los datos recogidos.
		in = new DataInputStream(socket.getInputStream());
		// getInputStream from the socket to read data from the client who has been connected

		this.nombre = in.readUTF(); // Leemos el nombre
		user.anadirUsuario(nombre, socket); // Añadimos un nuevo usuario a la sala de chat.
	}

	// Acciones a realizar el hilo.
	public void run() {
		try {
			// Si el resultado es 'true' se realiza lo siguiente, hasta que se convierta a 'false'.
			while (true) {
				String msg = in.readUTF(); // Leemos el mensaje y almacenamos.
				usuario.enviarMensaje(msg, nombre); // Mandamos el mensaje junto con el nombre de usuario
			}
			// Excepción en el caso que hubiera.
		} catch (Exception e) {
			e.printStackTrace(); // Mensaje detallado
			// Removemos al cliente de la lita si hay algún error.
			usuario.eliminarUsuario(this.nombre);
		}
	}
}
