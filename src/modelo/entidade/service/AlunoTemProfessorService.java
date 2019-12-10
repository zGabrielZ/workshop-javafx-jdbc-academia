package modelo.entidade.service;


import java.util.List;
import modelo.dao.AlunoTemProfessorDao;
import modelo.dao.DaoFactory;
import modelo.entidade.AlunoTemProfessor;

public class AlunoTemProfessorService {
	
	private AlunoTemProfessorDao alunoDao = DaoFactory.criarAlunoTemProfessorDao();

	public List<AlunoTemProfessor> encontrarTudo(){
		return alunoDao.encontrarTudoAlunoProfessor();
	}
	
	public List<AlunoTemProfessor> encontrarPorNome(String nome){
		return alunoDao.encontrarPorNomeAluno(nome);
	}
	
	public void salvar(AlunoTemProfessor alunoTemProfessor) {
		if(alunoTemProfessor.getIdAlunoTemProfessor() == null) {
			alunoDao.inserir(alunoTemProfessor);}
	}
	
	public void remover(AlunoTemProfessor alunoTemProfessor) {
		alunoDao.remover(alunoTemProfessor.getIdAlunoTemProfessor());
	}
}
