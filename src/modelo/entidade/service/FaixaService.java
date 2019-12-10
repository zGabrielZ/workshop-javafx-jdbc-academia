package modelo.entidade.service;


import java.util.List;

import modelo.dao.DaoFactory;
import modelo.dao.FaixaDao;
import modelo.entidade.Faixa;

public class FaixaService {
	
	private FaixaDao faixaDao = DaoFactory.criarFaixa();

	public List<Faixa> encontrarTudo(){
		return faixaDao.encontrarTudo();
	}
	
	public void salvarOuAtualizar(Faixa faixa) {
		if(faixa.getId() == null) {
			faixaDao.inserir(faixa);
		}
		else {
			faixaDao.atualizar(faixa);
		}
	}
	
	public void remover(Faixa faixa) {
		faixaDao.remover(faixa.getId());
	}
}
