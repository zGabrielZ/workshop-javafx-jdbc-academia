package modelo.dao;

import java.util.List;

import modelo.entidade.Aluno;

public interface AlunoDao {

	void inserir(Aluno aluno);
	void atualizar(Aluno aluno);
	void remover(Integer id);
	List<Aluno> encontrarTudoAluno();
	List<Aluno> encontrarPorNomeAluno(String nome);
}
