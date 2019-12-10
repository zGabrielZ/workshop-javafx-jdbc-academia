package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static void mostrarAlert(String titulo, String cabecalho, String contexto, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(contexto);
		alert.show();
	}

	public static Optional<ButtonType> mostrarConfirmation(String titulo, String contexto) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(contexto);
		return alert.showAndWait();
	}
}
