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
import modelo.dao.FaixaDao;
import modelo.entidade.Faixa;

public class FaixaDaoJDBC implements FaixaDao{

	private Connection conn = null;
	
	public FaixaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserir(Faixa faixa) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO faixa (nomeFaixa) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,faixa.getFaixa());
	
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					faixa.setId(id);
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
	public void remover(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM faixa WHERE idFaixa = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void atualizar(Faixa faixa) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE faixa SET nomeFaixa = ? WHERE  idFaixa = ?");

			st.setString(1,faixa.getFaixa());
			st.setInt(2,faixa.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
		}
		
	}

	@Override
	public List<Faixa> encontrarTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM faixa");
			rs = st.executeQuery();
			
			List<Faixa> list = new ArrayList<Faixa>();
			
			while (rs.next()) {
				Faixa faixa = instanciacaoFaixa(rs);
				list.add(faixa);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	private Faixa instanciacaoFaixa(ResultSet rs) throws SQLException {
		Faixa faixa = new Faixa();
		faixa.setId(rs.getInt("idFaixa"));
		faixa.setFaixa(rs.getString("nomeFaixa"));
		return faixa;
	}

}
