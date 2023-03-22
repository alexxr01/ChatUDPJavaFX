package servidor;

public class ChatServer {

	public static void main(String[] args) {
		/*
		 * AQUÍ SE INICIA EL SERVIDOR.
		 */
		Thread servidor = new Thread(new IniciarServidor());
		servidor.start(); // Lo iniciamos
		// Mostramos un mensaje
		System.out.println("El servidor ha iniciado, nombre=[" + servidor.getName() + "]");
		
	}

}