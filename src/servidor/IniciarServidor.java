package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;

// This is to start a server up to 10 threads.
//
public class IniciarServidor implements Runnable {
	Socket socket = null; // To communicate with the client who has accessed Server
	Usuario user = new Usuario(); // To manage users who is in the chat room
	ServerSocket server_socket = null; // To accept a client

	int count = 0;
	Thread thread[] = new Thread[10];
	// this server is designed to accept up to 10 connections from the clients.

	@Override
	public void run() {
		try {
			server_socket = new ServerSocket(8080);
			while (true) {
				socket = server_socket.accept(); // wait for the request from clients			

				thread[count] = new Thread(new ServicioChat(user, socket));
				thread[count].start();
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
