package modelo.dao;

import java.util.List;

import modelo.entidade.AlunoTemFaixa;

public interface AlunoTemFaixaDao {

	void inserir(AlunoTemFaixa alunoTemFaixa);
	void remover(Integer id);
	List<AlunoTemFaixa> encontrarPorNomeAluno(String nome);
	List<AlunoTemFaixa> encontrarTudoAlunoFaixa();
}
