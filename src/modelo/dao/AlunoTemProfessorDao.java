package modelo.dao;

import java.util.List;
import modelo.entidade.AlunoTemProfessor;

public interface AlunoTemProfessorDao {

	void inserir(AlunoTemProfessor alunoTemProfessor);
	void remover(Integer id);
	List<AlunoTemProfessor> encontrarTudoAlunoProfessor();
	List<AlunoTemProfessor> encontrarPorNomeAluno(String nome);
}
