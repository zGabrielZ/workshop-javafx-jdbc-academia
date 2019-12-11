package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import modelo.dao.LoginDao;
import modelo.entidade.Login;

public class LoginDaoJDBC implements LoginDao {
	
	private Connection conn = null;
	
	public LoginDaoJDBC(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public void inserir(Login login) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO login (usuarioLogin,senhaLogin) VALUES(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1,login.getUsuario());
			st.setString(2,login.getSenha());
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					login.setId(id);
				}
			}
			else {
				throw new DbException("erro,nenhuma linha afetada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
		}
		
	}

	@Override
	public void atualizar(Login login) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE login SET usuarioLogin = ? , senhaLogin = ? WHERE idLogin = ?");
			st.setString(1,login.getUsuario());
			st.setString(2,login.getSenha());
			st.setInt(3,login.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
		}
		
		
		
	}

	@Override
	public void remover(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM login WHERE idLogin = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public List<Login> encontrarTudoLogin() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select idLogin,usuarioLogin from login");
			rs = st.executeQuery();
			
			List<Login> list = new ArrayList<>();
			
			while(rs.next()) {
				Login login = instanciacaoLogin(rs);
				list.add(login);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	@Override
	public List<Login> encontrarPorNomeUsuario(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT idLogin,usuarioLogin FROM login WHERE usuarioLogin like ? ");
			st.setString(1,"%"+nome+"%");
			
			rs = st.executeQuery();
			
			List<Login> list = new ArrayList<>();
			
			while(rs.next()) {
				Login login = instanciacaoLogin(rs);
				list.add(login);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}
	
	@Override
	public boolean checkLogin(String usuario, String senha) {
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean check = false;
		
		try {
			st = conn.prepareStatement("SELECT * FROM login WHERE login.usuarioLogin = ? and login.senhaLogin = ?");
			
			st.setString(1,usuario);
			st.setString(2,senha);
			
			rs = st.executeQuery();
			
			check = rs.next();			
			return check;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharResultSet(rs);
			DB.fecharStatement(st);
		}
	}
	
	public Login instanciacaoLogin(ResultSet rs) throws SQLException {
		Login login  = new Login();
		login.setId(rs.getInt("idLogin"));
		login.setUsuario(rs.getString("usuarioLogin"));
		return login;
	}	

}
