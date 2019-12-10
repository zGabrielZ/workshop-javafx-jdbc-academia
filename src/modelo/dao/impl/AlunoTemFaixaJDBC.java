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
import modelo.dao.AlunoTemFaixaDao;
import modelo.entidade.AlunoTemFaixa;

public class AlunoTemFaixaJDBC implements AlunoTemFaixaDao {

	private Connection conn = null;
	
	public AlunoTemFaixaJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void inserir(AlunoTemFaixa alunoTemFaixa) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO aluno_tem_faixa (idAluno,idFaixa,DataAlunoTemFaixa) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1,alunoTemFaixa.getAluno().getId());
			st.setInt(2,alunoTemFaixa.getFaixa().getId());
			st.setDate(3,new java.sql.Date(alunoTemFaixa.getDataAlunoTemFaixa().getTime()));
	
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					alunoTemFaixa.setIdAlunoTemFaixa(id);
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
			st = conn.prepareStatement("DELETE FROM aluno_tem_faixa WHERE idAlunoTemFaixa = ?");
			st.setInt(1,id);
			
			st.executeUpdate();
		} catch (Exception e) {
			throw new DbIntegridadeException(e.getMessage());
		}
		
	}

	@Override
	public List<AlunoTemFaixa> encontrarPorNomeAluno(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select aluno_tem_faixa.idAlunoTemFaixa as id,aluno.nomeAluno as NomeDoAluno,faixa.nomeFaixa as Faixa ,aluno_tem_faixa.DataAlunoTemFaixa as DataFaixa from aluno inner join aluno_tem_faixa\r\n" + 
					"on aluno.idAluno = aluno_tem_faixa.idAluno inner join faixa\r\n" + 
					"on faixa.idFaixa = aluno_tem_faixa.idFaixa\r\n" + 
					"where nomeAluno like ?\r\n" + 
					"order by aluno_tem_faixa.DataAlunoTemFaixa;");
			st.setString(1,"%"+nome+"%");
			
			rs = st.executeQuery();
			
			List<AlunoTemFaixa> lista = new ArrayList<>();
			
			while (rs.next()) {
				AlunoTemFaixa a = new AlunoTemFaixa();
				
				a.setIdAlunoTemFaixa(rs.getInt("id"));
				
				a.setNomeAluno(rs.getString("NomeDoAluno"));
				
				
				a.setNomeFaixa(rs.getString("Faixa"));
				
				a.setDataAlunoTemFaixa(new java.util.Date(rs.getTimestamp("DataFaixa").getTime()));
				
				
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

	@Override
	public List<AlunoTemFaixa> encontrarTudoAlunoFaixa() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select aluno_tem_faixa.idAlunoTemFaixa as id,aluno.nomeAluno as NomeDoAluno,faixa.nomeFaixa as Faixa ,aluno_tem_faixa.DataAlunoTemFaixa as DataFaixa from aluno inner join aluno_tem_faixa\r\n" + 
					"on aluno.idAluno = aluno_tem_faixa.idAluno inner join faixa\r\n" + 
					"on faixa.idFaixa = aluno_tem_faixa.idFaixa\r\n" + 
					"order by aluno_tem_faixa.DataAlunoTemFaixa");
			rs = st.executeQuery();
			
			List<AlunoTemFaixa> lista = new ArrayList<AlunoTemFaixa>();
			
			while (rs.next()) {
				AlunoTemFaixa a = new AlunoTemFaixa();
				
				a.setIdAlunoTemFaixa(rs.getInt("id"));
				
				a.setNomeAluno(rs.getString("NomeDoAluno"));
				
				a.setNomeFaixa(rs.getString("Faixa"));
				
				a.setDataAlunoTemFaixa(new java.util.Date(rs.getTimestamp("DataFaixa").getTime()));
				
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
}
