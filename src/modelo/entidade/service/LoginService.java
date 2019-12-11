package modelo.entidade.service;
import java.util.List;
import modelo.dao.DaoFactory;
import modelo.dao.LoginDao;
import modelo.entidade.Login;

public class LoginService {
	
	private LoginDao loginDao = DaoFactory.criarLogin();

	public List<Login> encontrarTudo(){
		return loginDao.encontrarTudoLogin();
	}
	
	public List<Login> encontrarPorNome(String nome){
		return loginDao.encontrarPorNomeUsuario(nome);
	}
	
	public void salvarOuAtualizar(Login login) {
		if(login.getId() == null) {
			loginDao.inserir(login);
		}
		else {
			loginDao.atualizar(login);
		}
	}
	
	public void remover(Login login) {
		loginDao.remover(login.getId());
	}
	
	public boolean checkInLogin(Login login) {
		return loginDao.checkLogin(login.getUsuario(),login.getSenha());	
	}
}
