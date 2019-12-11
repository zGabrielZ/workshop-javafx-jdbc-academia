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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.entidade.Login;
import modelo.entidade.service.LoginService;
import modelo.exceptions.Validacao;

public class LoginFormController implements Initializable {
	
	private Login entidade;
	
	private LoginService loginService;
	
	private List<ListaDeAlteracaoDeDados> listaDeAlteracaoDeDados = new ArrayList<>();

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setEntidade(Login entidade) {
		this.entidade = entidade;
	}
	
	public void addListaDeAlteracaoDeDados(ListaDeAlteracaoDeDados dadosDeAlteracaoDeDados) {
		listaDeAlteracaoDeDados.add(dadosDeAlteracaoDeDados);
	}

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private PasswordField passFieldSenha;
	
	@FXML
	private Label lblErroUsuario;
	
	@FXML
	private Label lblErroSenha;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) throws ParseException {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}
		
		if(loginService == null) {
			throw new IllegalStateException("Service está nulo");
		}
		
		try {
			entidade = formularioDados();
			loginService.salvarOuAtualizar(entidade);
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();
		
	}
	
	private void inicializarNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtUsuario,70);
		Constraints.setTextFieldMaxLength(passFieldSenha,70);
	}
	
	private Login formularioDados() throws ParseException {
		Login login = new Login();
		
		Validacao validacao = new Validacao("erro");
		
		login.setId(Utils.converterParaInt(txtId.getText()));
		
		if(txtUsuario.getText() == null || txtUsuario.getText().trim().equals("")) {
			validacao.addErro("usuario","campo vázio");
		}
		
		login.setUsuario(txtUsuario.getText());
		
		if(passFieldSenha.getText() == null || passFieldSenha.getText().trim().equals("")) {
			validacao.addErro("senha","campo vázio");
		}
		
		login.setSenha(passFieldSenha.getText());
		
		if(validacao.getErros().size()>0) {
			throw validacao;
		}
		
		
		return login;
		
	}
	
	public void atualizarFormularioDados() {
		
		if(entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		
		txtId.setText(String.valueOf(entidade.getId()));
		txtUsuario.setText(entidade.getUsuario());
		passFieldSenha.setText("*************");
	}
	
	
	
	private void setErrosMensagens(Map<String,String> erros) {
		Set<String> campos = erros.keySet();
		
		if(campos.contains("usuario")) {
			lblErroUsuario.setText(erros.get("usuario"));
		}
		else {
			lblErroUsuario.setText("");
		}
		if(campos.contains("senha")) {
			lblErroSenha.setText(erros.get("senha"));
		}
		else {
			lblErroSenha.setText("");
		}
	}
	
	private void notificarListaDeAlteracaoDeDados() {
		for(ListaDeAlteracaoDeDados l : listaDeAlteracaoDeDados) {
			l.onDadosAlterados();
		}
	}
	
}
