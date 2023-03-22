package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;

public class ConectarServidor implements Runnable {
	Socket socket = null; // To open communication
	DataInputStream in = null; // To read data from Server
	DataOutputStream out = null; // To send data to Server

	public DataOutputStream getDataOuputStream() {
		return out;
	}

	@Override
	public void run() {

		try {
			socket = new Socket("localhost", 8080);

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(Cliente.name); // send 'name' to Server
			System.out.println(Cliente.name + " ha entrado al chat.");

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
