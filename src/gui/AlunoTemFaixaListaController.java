package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarConquistaDosAlunosTela;
import application.MainViewTela;
import db.DbIntegridadeException;
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
import modelo.entidade.AlunoTemFaixa;
import modelo.entidade.service.AlunoService;
import modelo.entidade.service.AlunoTemFaixaService;
import modelo.entidade.service.FaixaService;

public class AlunoTemFaixaListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<AlunoTemFaixa> tableViewAlunoTemFaixa;
	
	@FXML
	private TableColumn<AlunoTemFaixa, Integer> tableColumnId;

	@FXML
	private TableColumn<AlunoTemFaixa, String> tableColumnNomeAluno;

	@FXML
	private TableColumn<AlunoTemFaixa, String> tableColumnNomeFaixa;

	@FXML
	private TableColumn<AlunoTemFaixa, Date> tableColumnData;

	@FXML
	private TableColumn<AlunoTemFaixa, AlunoTemFaixa> tableComlumnRemove;

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
		AlunoTemFaixa alunoTemProfessor = new AlunoTemFaixa();
		criarFormulario(alunoTemProfessor, "/gui/AlunoTemFaixaForm.fxml", parentStage);
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
		MainViewTela mainView = new MainViewTela();
		CadastrarConquistaDosAlunosTela.getStage().close();
		try {
			mainView.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AlunoTemFaixaService alunoService;

	public void setAlunoService(AlunoTemFaixaService alunoService) {
		this.alunoService = alunoService;
	}

	private ObservableList<AlunoTemFaixa> obsList;

	private void inicializarTabela() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAlunoTemFaixa"));
		tableColumnNomeAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
		tableColumnNomeFaixa.setCellValueFactory(new PropertyValueFactory<>("nomeFaixa"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAlunoTemFaixa"));
		Utils.formatoDaTabelaDate(tableColumnData,"dd/MM/yyyy");
	}

	public void atualizarTableView() {
		if (alunoService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<AlunoTemFaixa> list = alunoService.encontrarTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewAlunoTemFaixa.setItems(obsList);
		botaoDeRemover();

	}

	private void criarFormulario(AlunoTemFaixa alunoTemFaixa, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AlunoTemFaixaFormController controller = loader.getController();
			controller.setEntidade(alunoTemFaixa);
			controller.setServices(new AlunoTemFaixaService(),new AlunoService(),new FaixaService());
			controller.carregarObjAssociado();
			controller.addListaDeAlteracaoDeDados(this);
			controller.atualizarFormularioDados();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do Aluno e do professor");
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

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<AlunoTemFaixa, AlunoTemFaixa>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(AlunoTemFaixa obj, boolean empty) {
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
	
	private void removerEntidade(AlunoTemFaixa obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmation("Confirmar","Tem certeza que quer deletar ? ");
		
		if(result.get() == ButtonType.OK) {
			if(alunoService == null) {
				throw new  IllegalStateException("Service está nulo");
			}
			
			try {
				alunoService.remover(obj);
				atualizarTableView();
			} catch (DbIntegridadeException e) {
				Alerts.mostrarAlert("Erro em remover","Erro",e.getMessage(),AlertType.ERROR);
			}
		}
	
	}
	public void buscar() { 
		List<AlunoTemFaixa> alunos =alunoService.encontrarPorNome(txtProcurarNome.getText());
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAlunoTemFaixa.setItems(obsList);
		
	}
	
	public void buscarLista() { 
		List<AlunoTemFaixa> alunos =alunoService.encontrarTudo();
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAlunoTemFaixa.setItems(obsList);
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
