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
import modelo.dao.ProfessorDao;
import modelo.entidade.Professor;
import modelo.enums.Sexo;

public class ProfessorDaoJDBC implements ProfessorDao {

	private Connection conn = null;
	
	public ProfessorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void inserir(Professor professor) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO professor (cpfProfessor,nomeProfessor,rgProfessor,dataNascimentoProfessor,"
					+ "sexoProfessor,estadoProfessor,cidadeProfessor,enderecoProfessor,bairroProfessor) VALUES(?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1,professor.getCpf());
			st.setString(2,professor.getNome());
			st.setInt(3,professor.getRg());
			st.setDate(4,new java.sql.Date(professor.getNascimento().getTime()));
			st.setString(5,professor.getSexo().toString());
			st.setString(6,professor.getEstado());
			st.setString(7,professor.getCidade());
			st.setString(8,professor.getEndereco());
			st.setString(9,professor.getBairro());
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					professor.setId(id);
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
	public void atualizar(Professor professor) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE professor SET cpfProfessor = ? , nomeProfessor = ? , rgProfessor = ? , dataNascimentoProfessor = ? ,"
					+ "sexoProfessor = ? , estadoProfessor = ? , cidadeProfessor = ? , enderecoProfessor = ? , bairroProfessor = ? WHERE idProfessor = ?");
			st.setInt(1,professor.getCpf());
			st.setString(2,professor.getNome());
			st.setInt(3,professor.getRg());
			st.setDate(4,new java.sql.Date(professor.getNascimento().getTime()));
			st.setString(5,professor.getSexo().toString());
			st.setString(6,professor.getEstado());
			st.setString(7,professor.getCidade());
			st.setString(8,professor.getEndereco());
			st.setString(9,professor.getBairro());
			st.setInt(10,professor.getId());
			
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
			st = conn.prepareStatement("DELETE FROM professor WHERE idProfessor = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public List<Professor> encontrarTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM professor");
			rs = st.executeQuery();
			
			List<Professor> list = new ArrayList<Professor>();
			
			while(rs.next()) {
				Professor professor = instanciacaoProfessor(rs);
				list.add(professor);
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
	public List<Professor> encontrarPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM professor WHERE nomeProfessor like ? ");
			st.setString(1,"%"+nome+"%");
			
			rs = st.executeQuery();
			
			List<Professor> list = new ArrayList<Professor>();
			
			while(rs.next()) {
				Professor professor = instanciacaoProfessor(rs);
				list.add(professor);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	public Professor instanciacaoProfessor(ResultSet rs) throws SQLException {
		Professor professor = new Professor();
		professor.setId(rs.getInt("idProfessor"));
		professor.setCpf(rs.getInt("cpfProfessor"));
		professor.setNome(rs.getString("nomeProfessor"));
		professor.setRg(rs.getInt("rgProfessor"));
		professor.setNascimento(new java.util.Date(rs.getTimestamp("dataNascimentoProfessor").getTime()));
		professor.setSexo(Sexo.valueOf(rs.getString("sexoProfessor")));
		professor.setEstado(rs.getString("estadoProfessor"));
		professor.setCidade(rs.getString("cidadeProfessor"));
		professor.setEndereco(rs.getString("enderecoProfessor"));
		professor.setBairro(rs.getString("bairroProfessor"));
		return professor;
	}

}
