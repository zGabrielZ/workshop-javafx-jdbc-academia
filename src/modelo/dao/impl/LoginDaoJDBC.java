package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import modelo.dao.LoginDao;

public class LoginDaoJDBC implements LoginDao {
	
	private Connection conn = null;
	
	public LoginDaoJDBC(Connection conn){
		this.conn = conn;
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

}
