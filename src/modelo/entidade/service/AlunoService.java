package modelo.entidade.service;
import java.util.List;

import modelo.dao.AlunoDao;
import modelo.dao.DaoFactory;
import modelo.entidade.Aluno;

public class AlunoService {
	
	private AlunoDao alunoDao = DaoFactory.criarAluno();

	public List<Aluno> encontrarTudo(){
		return alunoDao.encontrarTudoAluno();
	}
	
	public List<Aluno> encontrarPorNome(String nome){
		return alunoDao.encontrarPorNomeAluno(nome);
	}
	
	public void salvarOuAtualizar(Aluno aluno) {
		if(aluno.getId() == null) {
			alunoDao.inserir(aluno);
		}
		else {
			alunoDao.atualizar(aluno);
		}
	}
	
	public void remover(Aluno aluno) {
		alunoDao.remover(aluno.getId());
	}
}
