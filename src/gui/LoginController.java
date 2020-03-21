package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.LoginTela;
import application.MainViewTela;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.entidade.Login;
import modelo.entidade.service.LoginService;

public class LoginController implements Initializable{

	private LoginService loginService = new LoginService();
	
	@FXML
	private TextField txtFieldUsuario;
	
	@FXML
	private PasswordField passFieldSenha;
	
	@FXML
	private Button btEntrar;
	
	@FXML
	private Button btSair;
		
	@FXML
	public void onBtEntrarAction() throws IOException {
		
		String usuario = txtFieldUsuario.getText().toString();
		
		String senha = passFieldSenha.getText().toString();
		
		Login login = new Login(null,usuario, senha);
		
		if(loginService.checkInLogin(login)) {
			MainViewTela mainView = new MainViewTela();
			try {
				onBtSairAction();
				mainView.start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			Alerts.mostrarAlert("Error","Error ao entrar",null,AlertType.ERROR);
			txtFieldUsuario.setText("");
			passFieldSenha.setText("");
		}
		
		
	}
	

	@FXML
	public void onBtSairAction() {
		LoginTela.getStage().close();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	

	
	

}
