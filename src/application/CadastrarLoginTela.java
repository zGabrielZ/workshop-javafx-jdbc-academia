package application;

import gui.LoginListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.LoginService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarLoginTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/LoginListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			LoginListaController listaController = loader.getController();
			listaController.setLoginService(new LoginService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro de usuário");
			primaryStage.setScene(mainScene);
			primaryStage.show();
			setStage(primaryStage);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		CadastrarLoginTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarLoginTela.mainScene = mainScene;
	}
	
	
}
