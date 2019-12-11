package modelo.dao;

import java.util.List;

import modelo.entidade.Login;

public interface LoginDao {

	void inserir(Login login);
	void atualizar(Login login);
	void remover(Integer id);
	List<Login> encontrarTudoLogin();
	List<Login> encontrarPorNomeUsuario(String nome);
	boolean checkLogin(String usuario,String senha);
}
