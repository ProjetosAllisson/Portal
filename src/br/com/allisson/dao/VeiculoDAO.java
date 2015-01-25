package br.com.allisson.dao;

import java.util.List;

import br.com.allisson.modelo.coleta.Veiculo;

public class VeiculoDAO extends GenericDAO<Veiculo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VeiculoDAO(){
		super(Veiculo.class);
	}
	
	public List<Veiculo> findAllVeiculo(){
		return super.findAllResult(Veiculo.FIND_ALL, null);
	}
}
