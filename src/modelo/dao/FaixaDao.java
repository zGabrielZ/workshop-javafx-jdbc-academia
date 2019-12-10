package modelo.dao;

import java.util.List;

import modelo.entidade.Faixa;

public interface FaixaDao {

	void inserir(Faixa faixa);
	void remover(Integer id);
	void atualizar(Faixa faixa);
	List<Faixa> encontrarTudo();
}
