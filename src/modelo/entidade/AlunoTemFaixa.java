package modelo.entidade;

import java.io.Serializable;
import java.util.Date;

public class AlunoTemFaixa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idAlunoTemFaixa;
	private Date dataAlunoTemFaixa;
	private Integer idAluno;
	private Integer idFaixa;
	private Faixa faixa;
	
	private String nomeAluno;
	private String nomeFaixa;
	private Aluno aluno;
	
	public AlunoTemFaixa() {}
	
	public AlunoTemFaixa(Integer idAlunoTemFaixa, Date dataAlunoTemFaixa, Integer idAluno, Integer idFaixa) {
		this.idAlunoTemFaixa = idAlunoTemFaixa;
		this.dataAlunoTemFaixa = dataAlunoTemFaixa;
		this.idAluno = idAluno;
		this.idFaixa = idFaixa;
	}
	
	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getNomeFaixa() {
		return nomeFaixa;
	}

	public void setNomeFaixa(String nomeFaixa) {
		this.nomeFaixa = nomeFaixa;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Integer getIdAlunoTemFaixa() {
		return idAlunoTemFaixa;
	}

	public void setIdAlunoTemFaixa(Integer idAlunoTemFaixa) {
		this.idAlunoTemFaixa = idAlunoTemFaixa;
	}

	public Date getDataAlunoTemFaixa() {
		return dataAlunoTemFaixa;
	}

	public void setDataAlunoTemFaixa(Date dataAlunoTemFaixa) {
		this.dataAlunoTemFaixa = dataAlunoTemFaixa;
	}

	public Integer getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Integer idAluno) {
		this.idAluno = idAluno;
	}

	public Integer getIdFaixa() {
		return idFaixa;
	}

	public void setIdFaixa(Integer idFaixa) {
		this.idFaixa = idFaixa;
	}

	public Faixa getFaixa() {
		return faixa;
	}

	public void setFaixa(Faixa faixa) {
		this.faixa = faixa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAlunoTemFaixa == null) ? 0 : idAlunoTemFaixa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlunoTemFaixa other = (AlunoTemFaixa) obj;
		if (idAlunoTemFaixa == null) {
			if (other.idAlunoTemFaixa != null)
				return false;
		} else if (!idAlunoTemFaixa.equals(other.idAlunoTemFaixa))
			return false;
		return true;
	}
	
}
