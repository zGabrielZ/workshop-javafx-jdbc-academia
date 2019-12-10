package modelo.entidade;

import java.io.Serializable;
import java.util.Date;

public class AlunoTemProfessor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idAlunoTemProfessor;
	private Integer idAluno;
	private Integer idProfessor;
	private Date data;
	private Professor professor;
	
	private String nomeAluno;
	private String nomeProfessor;
	private Aluno aluno;

	public AlunoTemProfessor() {}
	

	public AlunoTemProfessor(Integer idAlunoTemProfessor, Integer idAluno, Integer idProfessor, Date data) {
		this.idAlunoTemProfessor = idAlunoTemProfessor;
		this.idAluno = idAluno;
		this.idProfessor = idProfessor;
		this.data = data;
	}
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public Integer getIdAlunoTemProfessor() {
		return idAlunoTemProfessor;
	}
	
	public void setIdAlunoTemProfessor(Integer idAlunoTemProfessor) {
		this.idAlunoTemProfessor = idAlunoTemProfessor;
	}

	public Integer getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Integer idAluno) {
		this.idAluno = idAluno;
	}

	public Integer getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(Integer idProfessor) {
		this.idProfessor = idProfessor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	
	
}
