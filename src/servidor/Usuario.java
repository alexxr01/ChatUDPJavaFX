package servidor;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import javafx.application.Platform;

// Clase con la cual manejaremos a los usuarios, sus mensajes y demás.
// Es una clase importante y sencilla si se entiende bien.

public class Usuario {
	//Cuando el método de añadir usuario ha sido llamado, se añade al HashMap junto con su DataInputStream
	HashMap<String, DataOutputStream> clientmap = new HashMap<String, DataOutputStream>();

	String str; // Solo la utilizaremos para actualizar los registros. (Logs).
	
	// Método añadir nuevo usuario, junto con su id que será el nombre y el socket, para la comunicación.
	// Se utiliza synchronized para que los hilos vayan en orden y no vayan "a su bola".
	public synchronized void anadirUsuario(String name, Socket socket) {
		try {
			// Insertamos el nuevo usuario
			clientmap.put(name, new DataOutputStream(socket.getOutputStream()));
			// Enviamos un mensaje contruido manualmente, para indicar en el chat que se ha
			// unido un nuevo usuario al chat actual.
			enviarMensaje(name + " ha entrado.", "Servidor");
			// Excepciones en el caso que las hubiera, acompañado de mensajes.
		} catch (Exception e) {
			System.out.println("Ocurre un error al añadir un nuevo usuario al chat.");
			e.printStackTrace();
		}

	}

	// Ya sea porque se ha salido o por un error, contamos con el método eliminar cliente
	// Con método synchronized para llevar un orden.
	public synchronized void eliminarUsuario(String name) {
		try {
			// Removemos el cliente del HashMap.
			clientmap.remove(name);
			// Llamamos al método enviar mensaje, y enviamos un mensaje de salida, esta vez
			// construido manualmente por nosotros.
			enviarMensaje(name + " ha salido.", "Servidor");
			// Excepción en el caso que hubiera.
		} catch (Exception e) {
			System.out.println("Ocurre un error al eliminar el usuario del chat.");
			e.printStackTrace();
		}
	}

	// Método para enviar un mensake, acompañado del mensaje y el nombre
	// Se utiliza, de nuevo, el método synchronized para llevar un orden y que no haya un descontrol.
	public synchronized void enviarMensaje(String mensaje, String nombreUsuario) throws Exception {
		// Creamos la "estructura" del mensaje enviado, queda así: Alejandro: Hola que tal!
		str = nombreUsuario + " : " + mensaje;
		
		// Enviamos el mensaje a los demás clientes, de lo contrario nadie recibiría nada
		Iterator iterator = clientmap.keySet().iterator();
		// Mientras haya un siguiente registro, se continúa.
		while (iterator.hasNext()) {
			// Creamos una variable de tipo String.
			String clientname = (String) iterator.next();
			// Escribimos en el HashMap el mensaje para registrarlo y enviar.
			clientmap.get(clientname).writeUTF(nombreUsuario + ": " + mensaje); // Estructura del mensaje, igual que anteriormente.
		}
	}
}

