package application;

import gui.FaixaListaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.entidade.service.FaixaService;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CadastrarFaixaTela extends Application {

	private static Stage stage;
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/FaixaListagem.fxml"));
			loader.load();
			
			Parent parent = loader.getRoot();
			mainScene = new Scene(parent);		
			
			FaixaListaController listaController = loader.getController();
			listaController.setFaixaService(new FaixaService());
			listaController.atualizarTableView();
			
			primaryStage.setTitle("Cadastro da faixa");
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
		CadastrarFaixaTela.stage = stage;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		CadastrarFaixaTela.mainScene = mainScene;
	}
	
	
	
	
}
