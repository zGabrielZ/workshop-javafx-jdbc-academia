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
import gui.util.Constraints;
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
import javafx.scene.control.TextField;
import modelo.entidade.Aluno;
import modelo.entidade.service.AlunoService;
import modelo.enums.Sexo;
import modelo.exceptions.Validacao;

public class AlunoFormController implements Initializable {
	
	private Aluno entidade;
	
	private AlunoService alunoService;
	
	private List<ListaDeAlteracaoDeDados> listaDeAlteracaoDeDados = new ArrayList<>();

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public void setEntidade(Aluno entidade) {
		this.entidade = entidade;
	}
	
	public void addListaDeAlteracaoDeDados(ListaDeAlteracaoDeDados dadosDeAlteracaoDeDados) {
		listaDeAlteracaoDeDados.add(dadosDeAlteracaoDeDados);
	}

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtCPF;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtRg;
	
	@FXML
	private DatePicker dpNascimento;
	
	@FXML
	private ComboBox<Sexo> txtSexo;
	
	@FXML
	private TextField txtEstado;

	@FXML
	private TextField txtCidade;

	@FXML
	private TextField txtEndereco;
	
	@FXML
	private TextField txtBairro;
	
	@FXML
	private Label lblErroNome;
	
	@FXML
	private Label lblErroCPF;
	
	@FXML
	private Label lblErroRg;
	
	@FXML
	private Label lblErroNascimento;
	
	@FXML
	private Label lblErroSexo;
	
	@FXML
	private Label lblErroEstado;
	
	@FXML
	private Label lblErroCidade;
	
	@FXML
	private Label lblErroEndereco;
	
	@FXML
	private Label lblErroBairro;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btLimparCampos;
	
	private ObservableList<Sexo> obsList;
	
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
			alunoService.salvarOuAtualizar(entidade);
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
	
	@FXML
	public void onBtLimparCamposAction() {
		txtId.setText("");
		txtCPF.setText("");
		txtNome.setText("");
		txtRg.setText("");
		txtEstado.setText("");
		txtCidade.setText("");
		txtEndereco.setText("");
		txtBairro.setText("");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();
		
	}
	
	private void inicializarNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldInteger(txtCPF);
		Constraints.setTextFieldMaxLength(txtNome,70);
		Constraints.setTextFieldInteger(txtRg);
		Constraints.setTextFieldMaxLength(txtEstado,30);
		Constraints.setTextFieldMaxLength(txtCidade,30);
		Constraints.setTextFieldMaxLength(txtEndereco,30);
		Constraints.setTextFieldMaxLength(txtBairro,30);
		Utils.formatoDoDatePicker(dpNascimento,"dd/MM/yyyy");
		inicializarComboBoxSexo();
	}
	
	private Aluno formularioDados() throws ParseException {
		Aluno aluno = new Aluno();
		
		Validacao validacao = new Validacao("erro");
		
		aluno.setId(Utils.converterParaInt(txtId.getText()));
		
		if(txtCPF.getText() == null || txtCPF.getText().trim().equals("")) {
			validacao.addErro("cpf","campo vázio");
		}
		
		aluno.setCpf(Utils.converterParaInt((txtCPF.getText())));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			validacao.addErro("nome","campo vázio");
		}
		aluno.setNome(txtNome.getText());
		
		if(txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			validacao.addErro("rg","campo vázio");
		}
		aluno.setRg(Utils.converterParaInt(txtRg.getText()));
		
		if(txtEstado.getText() == null || txtEstado.getText().trim().equals("")) {
			validacao.addErro("estado","campo vázio");
		}
		aluno.setEstado(txtEstado.getText());
		
		if(txtCidade.getText() == null || txtCidade.getText().trim().equals("")) {
			validacao.addErro("cidade","campo vázio");
		}
		aluno.setCidade(txtCidade.getText());
		
		if(txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			validacao.addErro("endereco","campo vázio");
		}
		aluno.setEndereco(txtEndereco.getText());
		
		if(txtBairro.getText() == null || txtBairro.getText().trim().equals("")) {
			validacao.addErro("bairro","campo vázio");
		}
		aluno.setBairro(txtBairro.getText());
		
		if (dpNascimento.getValue() == null) {
			validacao.addErro("nascimento", "campo vázio");
		}
		else {
			Instant instant = Instant.from(dpNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			
			aluno.setNascimento(Date.from(instant));
		}
		
		aluno.setSexo(txtSexo.getValue());
		
		if(validacao.getErros().size()>0) {
			throw validacao;
		}
		
		
		return aluno;
		
	}
	
	public void atualizarFormularioDados() {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		
		txtId.setText(String.valueOf(entidade.getId()));
		txtCPF.setText(String.valueOf(entidade.getCpf()));
		txtNome.setText(entidade.getNome());
		txtRg.setText(String.valueOf(entidade.getRg()));
		txtEstado.setText(entidade.getEstado());
		txtCidade.setText(entidade.getCidade());
		txtEndereco.setText(entidade.getEndereco());
		txtBairro.setText(entidade.getBairro());
		if(entidade.getNascimento() !=null) {
			dpNascimento.setValue(LocalDateTime.ofInstant(entidade.getNascimento().toInstant(), ZoneId.systemDefault()).toLocalDate());
		}
		if(entidade.getSexo() ==null) {
			txtSexo.getSelectionModel().selectFirst();
		}
		else {
			txtSexo.setValue(entidade.getSexo());
		}
	}
	
	
	
	private void setErrosMensagens(Map<String,String> erros) {
		Set<String> campos = erros.keySet();
		
		if(campos.contains("nome")) {
			lblErroNome.setText(erros.get("nome"));
		}
		else {
			lblErroNome.setText("");
		}
		if(campos.contains("cpf")) {
			lblErroCPF.setText(erros.get("cpf"));
		}
		else {
			lblErroCPF.setText("");
		}
		if(campos.contains("rg")) {
			lblErroRg.setText(erros.get("rg"));
		}
		else {
			lblErroRg.setText("");
		}
		if(campos.contains("estado")) {
			lblErroEstado.setText(erros.get("estado"));
		}
		else {
			lblErroEstado.setText("");
		}
		if(campos.contains("cidade")) {
			lblErroCidade.setText(erros.get("cidade"));
		}
		else {
			lblErroCidade.setText("");
		}
		if(campos.contains("endereco")) {
			lblErroEndereco.setText(erros.get("endereco"));
		}
		else {
			lblErroEndereco.setText("");
		}
		if(campos.contains("bairro")) {
			lblErroBairro.setText(erros.get("bairro"));
		}
		else {
			lblErroBairro.setText("");
		}
		if(campos.contains("nascimento")) {
			lblErroNascimento.setText(erros.get("nascimento"));
		}
		else {
			lblErroNascimento.setText("");
		}
		if(campos.contains("sexo")) {
			lblErroSexo.setText(erros.get("sexo"));
		}
		else {
			lblErroSexo.setText("");
		}
	}
	
	public void carregarObjAssociado() {
		List<Sexo> list = new ArrayList<>();
		list.add(Sexo.M);
		list.add(Sexo.F);
		
		obsList = FXCollections.observableArrayList(list);
		txtSexo.setItems(obsList);
	}
	
	private void notificarListaDeAlteracaoDeDados() {
		for(ListaDeAlteracaoDeDados l : listaDeAlteracaoDeDados) {
			l.onDadosAlterados();
		}
	}
	
	private void inicializarComboBoxSexo() {
		Callback<ListView<Sexo>, ListCell<Sexo>> factory = lv -> new ListCell<Sexo>() {
			@Override
			protected void updateItem(Sexo item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.name());
			}
		};

		txtSexo.setCellFactory(factory);
		txtSexo.setButtonCell(factory.call(null));
	}
	
	
	
}
