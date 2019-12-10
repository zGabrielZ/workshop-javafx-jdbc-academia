package modelo.exceptions;

import java.util.HashMap;
import java.util.Map;

public class Validacao extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	private Map<String,String> erros = new HashMap<String, String>();
	
	public Validacao(String msg) {
		super(msg);
	}
	
	public Map<String,String> getErros(){
		return erros;
	}
	
	public void addErro(String campoNome,String erroDaMensagem) {
		erros.put(campoNome,erroDaMensagem);
	}
}
