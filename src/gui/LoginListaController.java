package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarLoginTela;
import application.LoginTela;
import db.DbException;
import gui.destinatario.ListaDeAlteracaoDeDados;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidade.Login;
import modelo.entidade.service.LoginService;

public class LoginListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<Login> tableViewLogin;

	@FXML
	private TableColumn<Login, Integer> tableColumnId;

	@FXML
	private TableColumn<Login, String> tableColumnUsuario;

	@FXML
	private TableColumn<Login, Login> tableComlumnEdit;

	@FXML
	private TableColumn<Login, Login> tableComlumnRemove;

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btProcurarNome;
	
	@FXML
	private Button btListaCompleta;
	
	@FXML
	private Button btVoltar;
	
	@FXML 
	private TextField txtProcurarNome;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.atualStage(event);
		Login aluno = new Login();
		criarFormulario(aluno, "/gui/LoginForm.fxml", parentStage);
	}
	
	@FXML
	public void onBtProcurarNome() {
		buscar();
	}
	
	@FXML
	public void onBtListaCompleta() {
		buscarLista();
	}
	
	@FXML
	public void onBtVoltar() {
		LoginTela loginTela = new LoginTela();
		CadastrarLoginTela.getStage().close();
		try {
			loginTela.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	private ObservableList<Login> obsList;

	private void inicializarTabela() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
	}

	public void atualizarTableView() {
		if (loginService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<Login> list = loginService.encontrarTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewLogin.setItems(obsList);
		botaoDeEditar();
		botaoDeRemover();

	}

	private void criarFormulario(Login login, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			LoginFormController controller = loader.getController();
			controller.setEntidade(login);
			controller.setLoginService(new LoginService());
			controller.addListaDeAlteracaoDeDados(this);
			controller.atualizarFormularioDados();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do Login");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);

			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.mostrarAlert("Io Exception", "Erro de carregamento da tela", e.getMessage(), AlertType.ERROR);
		}
	}

	private void botaoDeEditar() {
		tableComlumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnEdit.setCellFactory(param -> new TableCell<Login, Login>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Login obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> criarFormulario(obj, "/gui/LoginForm.fxml", Utils.atualStage(event)));
			}
		});
	}

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<Login, Login>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Login obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removeEntidade(obj));
			}
		});
	}
	
	private void removeEntidade(Login obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmation("Confirmar","Tem certeza que quer deletar ? ");
		
		if(result.get() == ButtonType.OK) {
			if(loginService == null) {
				throw new  IllegalStateException("Service está nulo");
			}
			
			try {
				loginService.remover(obj);
				atualizarTableView();
			} catch (DbException e) {
				Alerts.mostrarAlert("Erro em remover",null,e.getMessage(),AlertType.ERROR);
			}
		}
	
	}
	public void buscar() { 
		List<Login> alunos = loginService.encontrarPorNome(txtProcurarNome.getText());
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewLogin.setItems(obsList);
		
	}
	
	public void buscarLista() { 
		List<Login> alunos = loginService.encontrarTudo();
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewLogin.setItems(obsList);
	}
	
	@Override
	public void onDadosAlterados() {
		atualizarTableView();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarTabela();
	}

}
