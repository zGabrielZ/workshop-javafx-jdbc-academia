package application;

import gui.AlunoListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.AlunoService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarAlunoTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/AlunoListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			AlunoListaController listaController = loader.getController();
			listaController.setAlunoService(new AlunoService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro do aluno");
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
		CadastrarAlunoTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarAlunoTela.mainScene = mainScene;
	}
	
	
}
