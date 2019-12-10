package modelo.entidade;

import java.io.Serializable;
import java.util.Date;

import modelo.enums.Sexo;

public class Aluno implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer cpf;
	private String nome;
	private Integer rg;
	private Date nascimento;
	private Sexo sexo;
	private String estado;
	private String cidade;
	private String endereco;
	private String bairro;
	private AlunoTemProfessor alunoTemProfessor;
	private AlunoTemFaixa alunoTemFaixa;
	
	public Aluno() {}

	public Aluno(Integer id, Integer cpf, String nome, Integer rg, Date nascimento, Sexo sexo, String estado,
			String cidade, String endereco, String bairro) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.rg = rg;
		this.nascimento = nascimento;
		this.sexo = sexo;
		this.estado = estado;
		this.cidade = cidade;
		this.endereco = endereco;
		this.bairro = bairro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
		this.rg = rg;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public AlunoTemProfessor getAlunoTemProfessor() {
		return alunoTemProfessor;
	}

	public void setAlunoTemProfessor(AlunoTemProfessor alunoTemProfessor) {
		this.alunoTemProfessor = alunoTemProfessor;
	}
	
	public AlunoTemFaixa getAlunoTemFaixa() {
		return alunoTemFaixa;
	}

	public void setAlunoTemFaixa(AlunoTemFaixa alunoTemFaixa) {
		this.alunoTemFaixa = alunoTemFaixa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return id+", "+cpf+", "+nome+", "+rg+", "+nascimento+", "+sexo+", "+estado+", "+cidade+", "+endereco+", "+bairro; 
	}

	
}
