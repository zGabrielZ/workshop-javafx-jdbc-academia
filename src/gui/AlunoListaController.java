package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.CadastrarAlunoTela;
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
import modelo.entidade.Aluno;
import modelo.entidade.service.AlunoService;
import modelo.enums.Sexo;

public class AlunoListaController implements Initializable, ListaDeAlteracaoDeDados {

	@FXML
	private TableView<Aluno> tableViewAluno;

	@FXML
	private TableColumn<Aluno, Integer> tableColumnId;

	@FXML
	private TableColumn<Aluno, Integer> tableColumnCpf;

	@FXML
	private TableColumn<Aluno, String> tableColumnNome;

	@FXML
	private TableColumn<Aluno, Integer> tableColumnRg;

	@FXML
	private TableColumn<Aluno, Date> tableColumnData;

	@FXML
	private TableColumn<Aluno, Sexo> tableColumnSexo;

	@FXML
	private TableColumn<Aluno, String> tableColumnEstado;

	@FXML
	private TableColumn<Aluno, String> tableColumnCidade;

	@FXML
	private TableColumn<Aluno, String> tableColumnEndereco;

	@FXML
	private TableColumn<Aluno, String> tableColumnBairro;

	@FXML
	private TableColumn<Aluno, Aluno> tableComlumnEdit;

	@FXML
	private TableColumn<Aluno, Aluno> tableComlumnRemove;

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
		Aluno aluno = new Aluno();
		criarFormulario(aluno, "/gui/AlunoForm.fxml", parentStage);
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
		CadastrarAlunoTela.getStage().close();
		try {
			mainView.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AlunoService alunoService;

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	private ObservableList<Aluno> obsList;

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
		if (alunoService == null) {
			throw new IllegalStateException("Service está nulo");
		}

		List<Aluno> list = alunoService.encontrarTudo();

		obsList = FXCollections.observableArrayList(list);
		tableViewAluno.setItems(obsList);
		botaoDeEditar();
		botaoDeRemover();

	}

	private void criarFormulario(Aluno aluno, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AlunoFormController controller = loader.getController();
			controller.setEntidade(aluno);
			controller.setAlunoService(new AlunoService());
			controller.carregarObjAssociado();
			controller.addListaDeAlteracaoDeDados(this);
			controller.atualizarFormularioDados();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do Aluno");
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
		tableComlumnEdit.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> criarFormulario(obj, "/gui/AlunoForm.fxml", Utils.atualStage(event)));
			}
		});
	}

	private void botaoDeRemover() {
		tableComlumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableComlumnRemove.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
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
	
	private void removeEntidade(Aluno obj) {
		Optional<ButtonType> result = Alerts.mostrarConfirmation("Confirmar","Tem certeza que quer deletar ? ");
		
		if(result.get() == ButtonType.OK) {
			if(alunoService == null) {
				throw new  IllegalStateException("Service está nulo");
			}
			
			try {
				alunoService.remover(obj);
				atualizarTableView();
			} catch (DbException e) {
				Alerts.mostrarAlert("Erro em remover",null,e.getMessage(),AlertType.ERROR);
			}
		}
	
	}
	public void buscar() { 
		List<Aluno> alunos =alunoService.encontrarPorNome(txtProcurarNome.getText());
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAluno.setItems(obsList);
		
	}
	
	public void buscarLista() { 
		List<Aluno> alunos =alunoService.encontrarTudo();
		obsList = FXCollections.observableArrayList(alunos); 
		tableViewAluno.setItems(obsList);
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
