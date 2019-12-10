package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarFaixaTela;
import application.MainViewTela;
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
import modelo.entidade.Faixa;
import modelo.entidade.service.FaixaService;

public class FaixaListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<Faixa> tableViewFaixa;

	@FXML
	private TableColumn<Faixa, Integer> tableColumnId;

	@FXML
	private TableColumn<Faixa, String> tableColumnNome;

	@FXML
	private TableColumn<Faixa, Faixa> tableComlumnEdit;

	@FXML
	private TableColumn<Faixa, Faixa> tableComlumnRemove;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.atualStage(event);
		Faixa faixa = new Faixa();
		criarFormulario(faixa, "/gui/FaixaForm.fxml", parentStage);
	}
	
	@FXML
	public void onBtVoltar() {
		MainViewTela mainView = new MainViewTela();
		CadastrarFaixaTela.getStage().close();
		try {
			mainView.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private FaixaService faixaService;

	public void setFaixaService(FaixaService faixaService) {
		this.faixaService = faixaService;
	}

	private ObservableList<Faixa> obsList;
	
	
	public void selecionarItem(Faixa faixa) {
		if(faixa !=null) {
			txtId.setText(String.valueOf(faixa.getId()));
			txtNome.setText(faixa.getFaixa());
		}
		else {
			txtId.setText("");
			txtNome.setText("");
		}
	}

	private void inicializarTabela() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("faixa"));
	}

	public void atualizarTableView() {
		if (faixaService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<Faixa> list = faixaService.encontrarTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewFaixa.setItems(obsList);
		botaoDeEditar();
		botaoDeRemover();

	}

	private void criarFormulario(Faixa faixa, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			FaixaFormController controller = loader.getController();
			controller.setEntidade(faixa);
			controller.setFaixaService(new FaixaService());
			controller.addListaDeAlteracaoDeDados(this);
			controller.atualizarFormularioDados();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do Faixa");
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
		tableComlumnEdit.setCellFactory(param -> new TableCell<Faixa, Faixa>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Faixa obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> criarFormulario(obj, "/gui/FaixaForm.fxml", Utils.atualStage(event)));
			}
		});
	}

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<Faixa, Faixa>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Faixa obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removerEntidade(obj));
			}
		});
	}
	
	private void removerEntidade(Faixa obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmation("Confirmar","Tem certeza que quer deletar ? ");
		
		if(result.get() == ButtonType.OK) {
			if(faixaService == null) {
				throw new  IllegalStateException("Service está nulo");
			}
			
			try {
				faixaService.remover(obj);
				atualizarTableView();
			} catch (DbException e) {
				Alerts.mostrarAlert("Erro em remover",null,e.getMessage(),AlertType.ERROR);
			}
		}
	
	}
	
	@Override
	public void onDadosAlterados() {
		atualizarTableView();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarTabela();
		
		tableViewFaixa.getSelectionModel().selectedItemProperty().addListener((obsList,oldValor,novoValor) -> selecionarItem(novoValor));
	}
	
}
