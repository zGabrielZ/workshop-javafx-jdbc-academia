package modelo.dao;

import java.util.List;

import modelo.entidade.Professor;

public interface ProfessorDao {

	void inserir(Professor professor);
	void atualizar(Professor professor);
	void remover(Integer id);
	List<Professor> encontrarTudo();
	List<Professor> encontrarPorNome(String nome);
}
