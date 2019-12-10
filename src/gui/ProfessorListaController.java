package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarProfessorTela;
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
import modelo.entidade.Professor;
import modelo.entidade.service.ProfessorService;
import modelo.enums.Sexo;

public class ProfessorListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<Professor> tableViewProfessor;

	@FXML
	private TableColumn<Professor, Integer> tableColumnId;

	@FXML
	private TableColumn<Professor, Integer> tableColumnCpf;

	@FXML
	private TableColumn<Professor, String> tableColumnNome;

	@FXML
	private TableColumn<Professor, Integer> tableColumnRg;

	@FXML
	private TableColumn<Professor, Date> tableColumnData;

	@FXML
	private TableColumn<Professor, Sexo> tableColumnSexo;

	@FXML
	private TableColumn<Professor, String> tableColumnEstado;

	@FXML
	private TableColumn<Professor, String> tableColumnCidade;

	@FXML
	private TableColumn<Professor, String> tableColumnEndereco;

	@FXML
	private TableColumn<Professor, String> tableColumnBairro;

	@FXML
	private TableColumn<Professor, Professor> tableComlumnEdit;

	@FXML
	private TableColumn<Professor, Professor> tableComlumnRemove;

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
		Professor aluno = new Professor();
		criarFormulario(aluno, "/gui/ProfessorForm.fxml", parentStage);
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
		CadastrarProfessorTela.getStage().close();
		try {
			mainView.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ProfessorService professorService;

	public void setProfessorService(ProfessorService professorService) {
		this.professorService = professorService;
	}

	private ObservableList<Professor> obsList;

	private void inicializarTabela() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
		Utils.formatoDaTabelaDate(tableColumnData,"dd/MM/yyyy");
		tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		tableColumnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
	}

	public void atualizarTableView() {
		if (professorService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<Professor> list = professorService.encontratTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewProfessor.setItems(obsList);
		botaoDeEditar();
		botaoDeRemover();

	}

	private void criarFormulario(Professor professor, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProfessorFormController controller = loader.getController();
			controller.setEntidade(professor);
			controller.setProfessorService(new ProfessorService());
			controller.carregarObjAssociado();
			controller.addListaDeAlteracaoDeDados(this);
			controller.atualizarFormularioDados();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do Professor");
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
		tableComlumnEdit.setCellFactory(param -> new TableCell<Professor, Professor>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> criarFormulario(obj, "/gui/ProfessorForm.fxml", Utils.atualStage(event)));
			}
		});
	}

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<Professor, Professor>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
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
	
	private void removerEntidade(Professor obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmation("Confirmar","Tem certeza que quer deletar ? ");
		
		if(result.get() == ButtonType.OK) {
			if(professorService == null) {
				throw new  IllegalStateException("Service está nulo");
			}
			
			try {
				professorService.remover(obj);
				atualizarTableView();
			} catch (DbException e) {
				Alerts.mostrarAlert("Erro em remover",null,e.getMessage(),AlertType.ERROR);
			}
		}
	
	}
	public void buscar() { 
		List<Professor> professor  = professorService.encontrarPorNome(txtProcurarNome.getText());
		obsList = FXCollections.observableArrayList(professor); 
		tableViewProfessor.setItems(obsList);
		
	}
	
	public void buscarLista() { 
		List<Professor> alunos = professorService.encontratTudo();
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewProfessor.setItems(obsList);
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
