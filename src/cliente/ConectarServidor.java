package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;

public class ConectarServidor implements Runnable {
	Socket socket = null; // Necesario para abrir una comunicación.
	DataInputStream in = null; // Necesario para leer los datos del servidor.
	DataOutputStream out = null; // Para enviar los datos al servidor

	public DataOutputStream getDataOuputStream() {
		return out;
	}

	// Todas las acciones serán realizadas por el servidor
	@Override
	public void run() {

		try {
			// En primer lugar, se conectará al servidor mediante la IP localhost y el puerto 3000
			socket = new Socket("localhost", 3000);

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(Cliente.nombre); // send 'name' to Server
			System.out.println(Cliente.nombre + " ha entrado al chat.");

		} catch (IOException e) {
		}

		try {
			// This loop is to keep reading data from Server
			while (true) {
				String str = in.readUTF();
				Platform.runLater(() -> {
					Cliente.logs.setText(Cliente.logs.getText() + "\n" + str);
				});
			}
		} catch (IOException e) {
		}

	}
}
