package modelo.dao;

import db.DB;
import modelo.dao.impl.AlunoDaoJDBC;
import modelo.dao.impl.AlunoTemFaixaJDBC;
import modelo.dao.impl.AlunoTemProfessorJDBC;
import modelo.dao.impl.FaixaDaoJDBC;
import modelo.dao.impl.LoginDaoJDBC;
import modelo.dao.impl.ProfessorDaoJDBC;

public class DaoFactory {

	public static LoginDao criarLogin() {
		return new LoginDaoJDBC(DB.getConnection());
	}
	
	public static FaixaDao criarFaixa() {
		return new FaixaDaoJDBC(DB.getConnection());
	}
	
	public static ProfessorDao criarProfessor() {
		return new ProfessorDaoJDBC(DB.getConnection());
	}
	public static AlunoDao criarAluno() {
		return new AlunoDaoJDBC(DB.getConnection());
	}
	public static AlunoTemProfessorDao criarAlunoTemProfessorDao() {
		return new AlunoTemProfessorJDBC(DB.getConnection());
	}
	public static AlunoTemFaixaDao criarAlunoTemFaixaDao() {
		return new AlunoTemFaixaJDBC(DB.getConnection());
	}
}

