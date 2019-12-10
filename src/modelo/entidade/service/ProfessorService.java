package modelo.entidade.service;


import java.util.List;
import modelo.dao.DaoFactory;
import modelo.dao.ProfessorDao;
import modelo.entidade.Professor;

public class ProfessorService {
	
	private ProfessorDao professorDao = DaoFactory.criarProfessor();

	public List<Professor> encontratTudo(){
		return professorDao.encontrarTudo();
	}
	
	public List<Professor> encontrarPorNome(String nome){
		return professorDao.encontrarPorNome(nome);
	}
	
	public void salvarOuAtualizar(Professor professor) {
		if(professor.getId() == null) {
			professorDao.inserir(professor);
		}
		else {
			professorDao.atualizar(professor);
		}
	}
	
	public void remover(Professor professor) {
		professorDao.remover(professor.getId());
	}
}
