package application;

import gui.AlunoTemProfessorListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.AlunoTemProfessorService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarDiaDaAulaTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/AlunoTemProfessorListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			AlunoTemProfessorListaController listaController = loader.getController();
			listaController.setAlunoService(new AlunoTemProfessorService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro da aula");
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
		CadastrarDiaDaAulaTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarDiaDaAulaTela.mainScene = mainScene;
	}
	
	
	
}
