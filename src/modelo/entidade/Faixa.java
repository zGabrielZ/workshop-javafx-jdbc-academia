package modelo.entidade;

import java.io.Serializable;

public class Faixa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String faixa;
	
	public Faixa() {}

	public Faixa(Integer id, String faixa) {
		this.id = id;
		this.faixa = faixa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFaixa() {
		return faixa;
	}

	public void setFaixa(String faixa) {
		this.faixa = faixa;
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
		Faixa other = (Faixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id+", "+faixa;
	}
	
}
