package br.com.allisson.dao;

import java.util.List;

import br.com.allisson.modelo.ServicosDisponiveis;

public class ServicosDisponiveisDAO extends GenericDAO<ServicosDisponiveis> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ServicosDisponiveisDAO() {
		super(ServicosDisponiveis.class);
	}
	
	public List<ServicosDisponiveis> servicos(){
		
		
		return super.findAll();
	}
	
	public ServicosDisponiveis servico(){
		return super.findOneResult(ServicosDisponiveis.FIND_SERVICOS, null);
	}

	
	
}
