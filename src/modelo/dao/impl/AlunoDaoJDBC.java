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
import modelo.dao.AlunoDao;
import modelo.entidade.Aluno;
import modelo.enums.Sexo;

public class AlunoDaoJDBC implements AlunoDao {

	private Connection conn = null;
	
	public AlunoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Aluno aluno) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO aluno (cpfAluno,nomeAluno,rgAluno,dataNascimentoAluno,"
					+ "sexoAluno,estadoAluno,cidadeAluno,enderecoAluno,bairroAluno) VALUES(?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1,aluno.getCpf());
			st.setString(2,aluno.getNome());
			st.setInt(3,aluno.getRg());
			st.setDate(4,new java.sql.Date(aluno.getNascimento().getTime()));
			st.setString(5,aluno.getSexo().toString());
			st.setString(6,aluno.getEstado());
			st.setString(7,aluno.getCidade());
			st.setString(8,aluno.getEndereco());
			st.setString(9,aluno.getBairro());
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					aluno.setId(id);
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
	public void atualizar(Aluno aluno) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE aluno SET cpfAluno = ? , nomeAluno = ? , rgAluno = ? , dataNascimentoAluno = ? ,"
					+ "sexoAluno = ? , estadoAluno = ? , cidadeAluno = ? , enderecoAluno = ? , bairroAluno = ? WHERE idAluno = ?");
			st.setInt(1,aluno.getCpf());
			st.setString(2,aluno.getNome());
			st.setInt(3,aluno.getRg());
			st.setDate(4,new java.sql.Date(aluno.getNascimento().getTime()));
			st.setString(5,aluno.getSexo().toString());
			st.setString(6,aluno.getEstado());
			st.setString(7,aluno.getCidade());
			st.setString(8,aluno.getEndereco());
			st.setString(9,aluno.getBairro());
			st.setInt(10,aluno.getId());
			
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
			st = conn.prepareStatement("DELETE FROM aluno WHERE idAluno = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public List<Aluno> encontrarTudoAluno() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM aluno");
			rs = st.executeQuery();
			
			List<Aluno> list = new ArrayList<Aluno>();
			
			while(rs.next()) {
				Aluno aluno = instanciacaoAluno2(rs);
				list.add(aluno);
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
	public List<Aluno> encontrarPorNomeAluno(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM aluno WHERE nomeAluno like ? ");
			st.setString(1,"%"+nome+"%");
			
			rs = st.executeQuery();
			
			List<Aluno> list = new ArrayList<Aluno>();
			
			while(rs.next()) {
				Aluno aluno = instanciacaoAluno2(rs);
				list.add(aluno);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}
	
	public Aluno instanciacaoAluno2(ResultSet rs) throws SQLException {
		Aluno aluno = new Aluno();
		aluno.setId(rs.getInt("idAluno"));
		aluno.setCpf(rs.getInt("cpfAluno"));
		aluno.setNome(rs.getString("nomeAluno"));
		aluno.setRg(rs.getInt("rgAluno"));
		aluno.setNascimento(new java.util.Date(rs.getTimestamp("dataNascimentoAluno").getTime()));
		aluno.setSexo(Sexo.valueOf(rs.getString("sexoAluno")));
		aluno.setEstado(rs.getString("estadoAluno"));
		aluno.setCidade(rs.getString("cidadeAluno"));
		aluno.setEndereco(rs.getString("enderecoAluno"));
		aluno.setBairro(rs.getString("bairroAluno"));
		return aluno;
	}	
	
}
