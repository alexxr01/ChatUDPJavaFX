module ChatUDPJavaFX {
	requires javafx.controls;
	
	opens cliente to javafx.graphics, javafx.fxml;
	opens servidor to javafx.graphics, javafx.fxml;
}
