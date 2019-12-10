package application;

import gui.ProfessorListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.ProfessorService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarProfessorTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/ProfessorListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			ProfessorListaController listaController = loader.getController();
			listaController.setProfessorService(new ProfessorService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro do professor");
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
		CadastrarProfessorTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarProfessorTela.mainScene = mainScene;
	}
	
	
	
	
}
