package application;

import gui.AlunoTemFaixaListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.AlunoTemFaixaService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarConquistaDosAlunosTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/AlunoTemFaixaListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			AlunoTemFaixaListaController listaController = loader.getController();
			listaController.setAlunoService(new AlunoTemFaixaService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro da conquista do aluno");
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
		CadastrarConquistaDosAlunosTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarConquistaDosAlunosTela.mainScene = mainScene;
	}
	
	
	
	
}
