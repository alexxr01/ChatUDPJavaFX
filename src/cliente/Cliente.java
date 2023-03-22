package cliente;

import java.io.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Cliente extends Application {

	public static Label logs = new Label("› comienzo del chat\n");
	private TextField introducirNickName = new TextField();
	public static TextField mensajeEnviar = new TextField();
	private Scene scene1, scene2;
	private Button botonEntrar = new Button("Entrar");

	public static String nombre = "nombre";

	@Override
	public void start(Stage primaryStage) throws Exception {

		// scene1: enter name first
		GridPane root1 = new GridPane();
		root1.setPrefSize(400, 200);
		root1.setPadding(new Insets(0, 20, 20, 20)); // add 10px padding
		root1.setVgap(15); // set vertical gap
		root1.setHgap(5); // set horizontal gap
		root1.setAlignment(Pos.CENTER);
		root1.add(new Label("Introduce tu nombre: "), 0, 0);
		root1.add(introducirNickName, 0, 1);
		root1.add(botonEntrar, 1, 1);

		scene1 = new Scene(root1);
		primaryStage.setScene(scene1);
		primaryStage.setTitle("Sala de chat");
		primaryStage.show();

		ConectarServidor connectServer = new ConectarServidor();

		// scene1 -- submitName Button
		botonEntrar.setOnAction(e -> {
			// getText
			nombre = introducirNickName.getText();
			Thread connectServerThread = new Thread(connectServer);
			connectServerThread.start();

			// Switch to scene2: after submitName button clicked
			ScrollPane layout = new ScrollPane();
			layout.setPrefSize(400, 600);
			layout.setContent(logs);

			BorderPane root2 = new BorderPane();
			root2.setPrefSize(350, 400);
			root2.setCenter(layout);
			root2.setBottom(mensajeEnviar);

			scene2 = new Scene(root2);
			primaryStage.setScene(scene2);
		});

		// Si el cliente ha insertado un mensaje
		Cliente.mensajeEnviar.setOnKeyReleased(event -> {
			// Se enviará si se pulsa el ENTER
			if (event.getCode() == KeyCode.ENTER) {
				// Se envia el mensaje
				DataOutputStream out = connectServer.getDataOuputStream();
				// Se recoge el mensaje el una variable de tipo String
				String msg = Cliente.mensajeEnviar.getText();
				try {
					// Escribimos el mensaje en el ChatServer
					out.writeUTF(msg);
					// Excepción en el caso que hubiera.
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// Por último, la casilla de insertar un mensaje la borramos.
				mensajeEnviar.setText("");
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}