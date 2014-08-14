package br.com.allisson.dao;

import br.com.allisson.modelo.ContaEmail;

public class ContaEmailDAO extends GenericDAO<ContaEmail>{

	
	private static final long serialVersionUID = 1L;
	
	public ContaEmailDAO(){
		super(ContaEmail.class);
	}
	
	public ContaEmail findConta(){
		return super.findOneResult(ContaEmail.FIND_CONTA_EMAIL,null);
	}
	
	

}
