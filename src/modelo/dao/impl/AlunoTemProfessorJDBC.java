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
import db.DbIntegridadeException;
import modelo.dao.AlunoTemProfessorDao;
import modelo.entidade.AlunoTemProfessor;

public class AlunoTemProfessorJDBC implements AlunoTemProfessorDao {

	private Connection conn = null;
	
	public AlunoTemProfessorJDBC(Connection conn) {
		this.conn=conn;
	}
	
	@Override
	public void inserir(AlunoTemProfessor alunoTemProfessor) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO aluno_tem_professor (idAluno,idProfessor,data) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1,alunoTemProfessor.getAluno().getId());
			st.setInt(2,alunoTemProfessor.getProfessor().getId());
			st.setDate(3,new java.sql.Date(alunoTemProfessor.getData().getTime()));
	
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					alunoTemProfessor.setIdAlunoTemProfessor(id);
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
			st = conn.prepareStatement("DELETE FROM aluno_tem_professor WHERE idAlunoTemProfessor = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
		} catch (Exception e) {
			throw new DbIntegridadeException(e.getMessage());
		}
		
	}

	@Override
	public List<AlunoTemProfessor> encontrarTudoAlunoProfessor() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select aluno_tem_professor.idAlunoTemProfessor as id,aluno.nomeAluno as NomeDoAluno ,professor.nomeProfessor as NomeDoProfessor ,aluno_tem_professor.data as DataAula from aluno inner join aluno_tem_professor \r\n" + 
					"on aluno.idAluno = aluno_tem_professor.idAluno inner join professor\r\n" + 
					"on professor.idProfessor = aluno_tem_professor.idProfessor\r\n" + 
					"order by aluno_tem_professor.data");
			rs = st.executeQuery();
			
			List<AlunoTemProfessor> lista = new ArrayList<>();
			
			while (rs.next()) {
				AlunoTemProfessor a = new AlunoTemProfessor();
				
				a.setIdAlunoTemProfessor(rs.getInt("id"));
				
				a.setNomeAluno(rs.getString("NomeDoAluno"));
				
				
				a.setNomeProfessor(rs.getString("NomeDoProfessor"));
				
				a.setData(new java.util.Date(rs.getTimestamp("DataAula").getTime()));
				
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

	@Override
	public List<AlunoTemProfessor> encontrarPorNomeAluno(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select aluno_tem_professor.idAlunoTemProfessor as id,aluno.nomeAluno as NomeDoAluno ,professor.nomeProfessor as NomeDoProfessor ,aluno_tem_professor.data as DataAula from aluno inner join aluno_tem_professor \r\n" + 
					"on aluno.idAluno = aluno_tem_professor.idAluno inner join professor\r\n" + 
					"on professor.idProfessor = aluno_tem_professor.idProfessor\r\n" + 
					"where nomeAluno like ?"
					+ "order by aluno_tem_professor.data");
			st.setString(1,"%"+nome+"%");
			
			rs = st.executeQuery();
			
			List<AlunoTemProfessor> lista = new ArrayList<>();
			
			while (rs.next()) {
				AlunoTemProfessor a = new AlunoTemProfessor();
				
				a.setIdAlunoTemProfessor(rs.getInt("id"));
				
				a.setNomeAluno(rs.getString("NomeDoAluno"));
				
				
				a.setNomeProfessor(rs.getString("NomeDoProfessor"));
				
				a.setData(new java.util.Date(rs.getTimestamp("DataAula").getTime()));
				
				
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}

}
