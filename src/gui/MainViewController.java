package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.AboutTela;
import application.CadastrarAlunoTela;
import application.CadastrarConquistaDosAlunosTela;
import application.CadastrarDiaDaAulaTela;
import application.CadastrarFaixaTela;
import application.CadastrarProfessorTela;
import application.MainViewTela;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainViewController implements Initializable {

	@FXML
	private Button btCadastrarAluno;
	
	@FXML
	private Button btCadastrarProfessor;
	
	@FXML
	private Button btCadastrarFaixa;
	
	@FXML
	private Button btCadastrarDiaDaAula;
	
	@FXML
	private Button btCadastrarConquistaDosAlunos;
	
	@FXML
	private Button btSobre;

	@FXML
	private Button btSair;
	
	
	@FXML
	public void onBtCadastrarAlunoAction() {
		CadastrarAlunoTela cadastrarAluno = new CadastrarAlunoTela();
		MainViewTela.getStage().close();
		try {
			cadastrarAluno.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtCadastrarProfessorAction() {
		CadastrarProfessorTela cadastrarProfessor = new CadastrarProfessorTela();
		MainViewTela.getStage().close();
		try {
			cadastrarProfessor.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtCadastrarFaixaAction() {
		CadastrarFaixaTela cadastrarFaixa = new CadastrarFaixaTela();
		MainViewTela.getStage().close();
		try {
			cadastrarFaixa.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtCadastrarDiaDaAulaAction() {
		CadastrarDiaDaAulaTela cadastrarDiaDaAula = new CadastrarDiaDaAulaTela();
		MainViewTela.getStage().close();
		try {
			cadastrarDiaDaAula.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtCadastrarConquistaDosAlunosAction() {
		CadastrarConquistaDosAlunosTela cadastrarConquistaDosAlunos = new CadastrarConquistaDosAlunosTela();
		MainViewTela.getStage().close();
		try {
			cadastrarConquistaDosAlunos.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtSobreAction() {
		AboutTela aboutTela = new AboutTela();
		try {
			aboutTela.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtSairAction() {
		MainViewTela.getStage().close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
