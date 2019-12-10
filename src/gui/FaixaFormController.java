package gui;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.destinatario.ListaDeAlteracaoDeDados;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.entidade.Faixa;
import modelo.entidade.service.FaixaService;
import modelo.exceptions.Validacao;

public class FaixaFormController implements Initializable {
	
	private Faixa entidade;
	
	private FaixaService faixaService;
	
	private List<ListaDeAlteracaoDeDados> listaDeAlteracaoDeDados = new ArrayList<>();

	public void setFaixaService(FaixaService faixaService) {
		this.faixaService = faixaService;
	}

	public void setEntidade(Faixa entidade) {
		this.entidade = entidade;
	}
	
	public void addListaDeAlteracaoDeDados(ListaDeAlteracaoDeDados dadosDeAlteracaoDeDados) {
		listaDeAlteracaoDeDados.add(dadosDeAlteracaoDeDados);
	}

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label lblErroNome;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btLimparCampos;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) throws ParseException {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}
		
		if(faixaService == null) {
			throw new IllegalStateException("Service está nulo");
		}
		
		try {
			entidade = getFormData();
			faixaService.salvarOuAtualizar(entidade);
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
		txtNome.setText("");
	}
	
	
	private void inicializarNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome,70);
	}
	
	private Faixa getFormData() throws ParseException {
		Faixa faixa = new Faixa();
		
		Validacao validacao = new Validacao("error");
		
		faixa.setId(Utils.converterParaInt(txtId.getText()));
			
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			validacao.addErro("nome","campo vázio");
		}
		faixa.setFaixa(txtNome.getText());
		
		if(validacao.getErros().size()>0) {
			throw validacao;
		}
		
		
		return faixa;
		
	}
	
	public void atualizarFormularioDados() {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getFaixa());
	}
	
	
	private void setErrosMensagens(Map<String,String> erros) {
		Set<String> campos = erros.keySet();
		
		if(campos.contains("nome")) {
			lblErroNome.setText(erros.get("nome"));
		}
		else {
			lblErroNome.setText("");
		}
	}
	
	private void notificarListaDeAlteracaoDeDados() {
		for(ListaDeAlteracaoDeDados l : listaDeAlteracaoDeDados) {
			l.onDadosAlterados();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();
		
	}
	
	
}
