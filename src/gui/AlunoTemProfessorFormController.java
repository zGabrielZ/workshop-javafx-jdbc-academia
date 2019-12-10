package gui;

import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.destinatario.ListaDeAlteracaoDeDados;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import modelo.entidade.Aluno;
import modelo.entidade.AlunoTemProfessor;
import modelo.entidade.Professor;
import modelo.entidade.service.AlunoService;
import modelo.entidade.service.AlunoTemProfessorService;
import modelo.entidade.service.ProfessorService;
import modelo.exceptions.Validacao;

public class AlunoTemProfessorFormController implements Initializable {
	
	private AlunoTemProfessor entidade;
	
	private AlunoTemProfessorService alunoTemProfessorService;
	
	private AlunoService alunoService;
	
	private ProfessorService professorService;
	
	private List<ListaDeAlteracaoDeDados> listaDeAlteracaoDeDados = new ArrayList<>();
	
	
	public void setAlunoTemProfessorService(AlunoTemProfessorService alunoTemProfessorService) {
		this.alunoTemProfessorService = alunoTemProfessorService;
	}

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public void setProfessorService(ProfessorService professorService) {
		this.professorService = professorService;
	}

	public void setEntidade(AlunoTemProfessor entidade) {
		this.entidade = entidade;
	}
	
	public void setServices(AlunoTemProfessorService alunoTemProfessorService,AlunoService alunoService,ProfessorService professorService) {
		this.alunoService = alunoService;
		this.alunoTemProfessorService = alunoTemProfessorService;
		this.professorService = professorService;
	}
	
	public void addListaDeAlteracaoDeDados(ListaDeAlteracaoDeDados dadosDeAlteracaoDeDados) {
		listaDeAlteracaoDeDados.add(dadosDeAlteracaoDeDados);
	}

	@FXML
	private ComboBox<Aluno> comboBoxNomeAluno;
	
	@FXML
	private ComboBox<Professor> comboBoxNomeProfessor;
	
	@FXML
	private DatePicker dpNascimento;
	
	@FXML
	private Label lblErroNomeAluno;
	
	@FXML
	private Label lblErroNomeProfessor;
	
	@FXML
	private Label lblErroData;

	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	private ObservableList<Aluno> obsListAluno;
	
	private ObservableList<Professor> obsListProfessor;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) throws ParseException {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}
		
		if(alunoService == null) {
			throw new IllegalStateException("Service está nulo");
		}
		
		try {
			entidade = formularioDados();
			alunoTemProfessorService.salvar(entidade);
			notificarListaDeAlteracaoDeDados();
			Utils.atualStage(event).close();
		}catch (Validacao e) {
			setErrosMensagens(e.getErros());
		}catch (DbException e) {
			Alerts.mostrarAlert("Erro em salvar",null,e.getMessage(),AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.atualStage(event).close();
	}
	
	private void inicializarNodes() {
		Utils.formatoDoDatePicker(dpNascimento,"dd/MM/yyyy");
		inicializarComboBoxAluno();
		inicializarComboBoxProfessor();
	}
	
	
	private AlunoTemProfessor formularioDados() throws ParseException {
		AlunoTemProfessor alunoTemProfessor = new AlunoTemProfessor();
		
		Validacao validacao = new Validacao("error");
		
		
		if (dpNascimento.getValue() == null) {
			validacao.addErro("data", "campo vázio");
		}
		else {
			Instant instant = Instant.from(dpNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			
			alunoTemProfessor.setData(Date.from(instant));
		}
		
		alunoTemProfessor.setAluno(comboBoxNomeAluno.getValue());
		
		alunoTemProfessor.setProfessor(comboBoxNomeProfessor.getValue());
		
		
		if(validacao.getErros().size()>0) {
			throw validacao;
		}
		
		
		return alunoTemProfessor;
		
	}
	
	public void atualizarFormularioDados() {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		if(entidade.getData() !=null) {
			dpNascimento.setValue(LocalDateTime.ofInstant(entidade.getData().toInstant(), ZoneId.systemDefault()).toLocalDate());
			
		}
		
		if(entidade.getAluno() == null) {
			comboBoxNomeAluno.getSelectionModel().selectFirst();
		}
		else {
			comboBoxNomeAluno.setValue(entidade.getAluno());
		}
		
		if(entidade.getProfessor() == null) {
			comboBoxNomeProfessor.getSelectionModel().selectFirst();
		}
		else {
			comboBoxNomeProfessor.setValue(entidade.getProfessor());
		}
	}
	
	private void setErrosMensagens(Map<String,String> erros) {
		Set<String> campos = erros.keySet();
		
		if(campos.contains("data")) {
			lblErroData.setText(erros.get("data"));
		}
		else {
			lblErroData.setText("");
		}
	}
	
	public void carregarObjAssociado() {
		if (alunoService == null) {
			throw new IllegalStateException("AlunoService está nulo");
		}
		
		if (professorService == null) {
			throw new IllegalStateException("ProfessorService está nulo");
		}

		List<Aluno> aluno = alunoService.encontrarTudo();
		obsListAluno = FXCollections.observableArrayList(aluno);
		comboBoxNomeAluno.setItems(obsListAluno);
		
		List<Professor> professor = professorService.encontratTudo();
		obsListProfessor = FXCollections.observableArrayList(professor);
		comboBoxNomeProfessor.setItems(obsListProfessor);

	}
	
	private void notificarListaDeAlteracaoDeDados() {
		for(ListaDeAlteracaoDeDados l : listaDeAlteracaoDeDados) {
			l.onDadosAlterados();
		}
	}
	
	private void inicializarComboBoxAluno() {
		Callback<ListView<Aluno>, ListCell<Aluno>> factory = lv -> new ListCell<Aluno>() {
			@Override
			protected void updateItem(Aluno item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};

		comboBoxNomeAluno.setCellFactory(factory);
		comboBoxNomeAluno.setButtonCell(factory.call(null));
	}
	
	private void inicializarComboBoxProfessor() {
		Callback<ListView<Professor>, ListCell<Professor>> factory = lv -> new ListCell<Professor>() {
			@Override
			protected void updateItem(Professor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};

		comboBoxNomeProfessor.setCellFactory(factory);
		comboBoxNomeProfessor.setButtonCell(factory.call(null));
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();
	}
	
	
}
