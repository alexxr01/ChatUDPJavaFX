package servidor;

public class ChatServer {

	public static void main(String[] args) {
		/*
		 * AQUÍ SE INICIA EL SERVIDOR.
		 */
		Thread startServer = new Thread(new IniciarServidor());
		startServer.start();
		System.out.println("El servidor ha iniciado.");
		
	}

}