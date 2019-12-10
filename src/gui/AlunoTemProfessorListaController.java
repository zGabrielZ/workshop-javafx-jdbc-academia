package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarDiaDaAulaTela;
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
import modelo.entidade.AlunoTemProfessor;
import modelo.entidade.service.AlunoService;
import modelo.entidade.service.AlunoTemProfessorService;
import modelo.entidade.service.ProfessorService;

public class AlunoTemProfessorListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<AlunoTemProfessor> tableViewAlunoTemProfessor;
	
	@FXML
	private TableColumn<AlunoTemProfessor, Integer> tableColumnId;

	@FXML
	private TableColumn<AlunoTemProfessor, String> tableColumnNomeAluno;

	@FXML
	private TableColumn<AlunoTemProfessor, String> tableColumnNomeProfessor;

	@FXML
	private TableColumn<AlunoTemProfessor, Date> tableColumnData;

	@FXML
	private TableColumn<AlunoTemProfessor, AlunoTemProfessor> tableComlumnRemove;

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
		AlunoTemProfessor alunoTemProfessor = new AlunoTemProfessor();
		crirarFormulario(alunoTemProfessor, "/gui/AlunoTemProfessorForm.fxml", parentStage);
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
	public void obBtVoltar() {
		MainViewTela mainView = new MainViewTela();
		CadastrarDiaDaAulaTela.getStage().close();
		try {
			mainView.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AlunoTemProfessorService alunoService;

	public void setAlunoService(AlunoTemProfessorService alunoService) {
		this.alunoService = alunoService;
	}

	private ObservableList<AlunoTemProfessor> obsList;

	private void inicializarTabela() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAlunoTemProfessor"));
		tableColumnNomeAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
		tableColumnNomeProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		Utils.formatoDaTabelaDate(tableColumnData,"dd/MM/yyyy");
	}

	public void atualizarTableView() {
		if (alunoService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<AlunoTemProfessor> list = alunoService.encontrarTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewAlunoTemProfessor.setItems(obsList);
		botaoDeRemover();

	}

	private void crirarFormulario(AlunoTemProfessor alunoTemProfessor, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AlunoTemProfessorFormController controller = loader.getController();
			controller.setEntidade(alunoTemProfessor);
			controller.setServices(new AlunoTemProfessorService(),new AlunoService(),new ProfessorService());
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

	@Override
	public void onDadosAlterados() {
		atualizarTableView();
	}

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<AlunoTemProfessor, AlunoTemProfessor>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(AlunoTemProfessor obj, boolean empty) {
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
	
	private void removerEntidade(AlunoTemProfessor obj) {
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
		List<AlunoTemProfessor> alunos =alunoService.encontrarPorNome(txtProcurarNome.getText());
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAlunoTemProfessor.setItems(obsList);
		
	}
	
	public void buscarLista() { 
		List<AlunoTemProfessor> alunos =alunoService.encontrarTudo();
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAlunoTemProfessor.setItems(obsList);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarTabela();
	}


}
