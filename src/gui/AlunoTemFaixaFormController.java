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
import modelo.entidade.AlunoTemFaixa;
import modelo.entidade.Faixa;
import modelo.entidade.service.AlunoService;
import modelo.entidade.service.AlunoTemFaixaService;
import modelo.entidade.service.FaixaService;
import modelo.exceptions.Validacao;

public class AlunoTemFaixaFormController implements Initializable {
	
	private AlunoTemFaixa entidade;
	
	private AlunoTemFaixaService alunoTemProfessorService;
	
	private AlunoService alunoService;
	
	private FaixaService faixaService;
	
	private List<ListaDeAlteracaoDeDados> listaDeAlteracaoDeDados = new ArrayList<>();
	
	
	public void setAlunoTemFaixaService(AlunoTemFaixaService alunoTemProfessorService) {
		this.alunoTemProfessorService = alunoTemProfessorService;
	}

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public void setFaixaService(FaixaService faixaService) {
		this.faixaService = faixaService;
	}

	public void setEntidade(AlunoTemFaixa entidade) {
		this.entidade = entidade;
	}
	
	public void setServices(AlunoTemFaixaService alunoTemProfessorService,AlunoService alunoService,FaixaService faixaService) {
		this.alunoService = alunoService;
		this.alunoTemProfessorService = alunoTemProfessorService;
		this.faixaService = faixaService;
	}
	
	public void addListaDeAlteracaoDeDados(ListaDeAlteracaoDeDados dadosDeAlteracaoDeDados) {
		listaDeAlteracaoDeDados.add(dadosDeAlteracaoDeDados);
	}

	@FXML
	private ComboBox<Aluno> comboBoxNomeAluno;
	
	@FXML
	private ComboBox<Faixa> comboBoxNomeFaixa;
	
	@FXML
	private DatePicker dpNascimento;
	
	@FXML
	private Label lblErroNomeAluno;
	
	@FXML
	private Label lblErroNomeFaixa;
	
	@FXML
	private Label lblErroData;

	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	private ObservableList<Aluno> obsListAluno;
	
	private ObservableList<Faixa> obsListFaixa;
	
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
			setErrosMenssagens(e.getErros());
		}catch (DbException e) {
			Alerts.mostrarAlert("Erro em salvar",null,e.getMessage(),AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.atualStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();
		
	}
	
	private void inicializarNodes() {
		Utils.formatoDoDatePicker(dpNascimento,"dd/MM/yyyy");
		inicializarComboBoxAluno();
		inicializarComboBoxFaixa();
	}
	
	private AlunoTemFaixa formularioDados() throws ParseException {
		AlunoTemFaixa alunoTemFaixa = new AlunoTemFaixa();
		
		Validacao validacao = new Validacao("error");
		
		
		if (dpNascimento.getValue() == null) {
			validacao.addErro("data", "campo vázio");
		}
		else {
			Instant instant = Instant.from(dpNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			
			alunoTemFaixa.setDataAlunoTemFaixa(Date.from(instant));
		}
		
		alunoTemFaixa.setAluno(comboBoxNomeAluno.getValue());
		
		alunoTemFaixa.setFaixa(comboBoxNomeFaixa.getValue());
		
		
		if(validacao.getErros().size()>0) {
			throw validacao;
		}
		
		
		return alunoTemFaixa;
		
	}
	
	public void atualizarFormularioDados() {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		if(entidade.getDataAlunoTemFaixa() !=null) {
			dpNascimento.setValue(LocalDateTime.ofInstant(entidade.getDataAlunoTemFaixa().toInstant(), ZoneId.systemDefault()).toLocalDate());
			
		}
		
		if(entidade.getAluno() == null) {
			comboBoxNomeAluno.getSelectionModel().selectFirst();
		}
		else {
			comboBoxNomeAluno.setValue(entidade.getAluno());
		}
		
		if(entidade.getFaixa() == null) {
			comboBoxNomeFaixa.getSelectionModel().selectFirst();
		}
		else {
			comboBoxNomeFaixa.setValue(entidade.getFaixa());
		}
	}
	
	private void setErrosMenssagens(Map<String,String> erros) {
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
		
		if (faixaService == null) {
			throw new IllegalStateException("FaixaService está nulo");
		}

		List<Aluno> aluno = alunoService.encontrarTudo();
		obsListAluno = FXCollections.observableArrayList(aluno);
		comboBoxNomeAluno.setItems(obsListAluno);
		
		List<Faixa> faixa = faixaService.encontrarTudo();
		obsListFaixa = FXCollections.observableArrayList(faixa);
		comboBoxNomeFaixa.setItems(obsListFaixa);

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
	
	private void inicializarComboBoxFaixa() {
		Callback<ListView<Faixa>, ListCell<Faixa>> factory = lv -> new ListCell<Faixa>() {
			@Override
			protected void updateItem(Faixa item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getFaixa());
			}
		};

		comboBoxNomeFaixa.setCellFactory(factory);
		comboBoxNomeFaixa.setButtonCell(factory.call(null));
	}
	
	
	
}
