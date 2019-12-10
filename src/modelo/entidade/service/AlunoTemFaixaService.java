package modelo.entidade.service;


import java.util.List;

import modelo.dao.AlunoTemFaixaDao;
import modelo.dao.DaoFactory;
import modelo.entidade.AlunoTemFaixa;

public class AlunoTemFaixaService {
	
	private AlunoTemFaixaDao alunoDao = DaoFactory.criarAlunoTemFaixaDao();

	public List<AlunoTemFaixa> encontrarTudo(){
		return alunoDao.encontrarTudoAlunoFaixa();
	}
	
	public List<AlunoTemFaixa> encontrarPorNome(String nome){
		return alunoDao.encontrarPorNomeAluno(nome);
	}
	
	public void salvar(AlunoTemFaixa alunoTemFaixa) {
		if(alunoTemFaixa.getIdAlunoTemFaixa() == null) {
			alunoDao.inserir(alunoTemFaixa);}
	}
	
	public void remover(AlunoTemFaixa alunoTemFaixa) {
		alunoDao.remover(alunoTemFaixa.getIdAlunoTemFaixa());
	}
}
