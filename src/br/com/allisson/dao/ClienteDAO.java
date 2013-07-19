package br.com.allisson.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.allisson.modelo.Cliente;


public class ClienteDAO extends GenericDAO<Cliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1817413732492918799L;
	
	public ClienteDAO(){
		super(Cliente.class);
	}
	
	public Cliente findCliente(String cnpj){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cgc", cnpj);
		
		return super.findOneResult(Cliente.FIND_CLIENTE, parameters);
	}

}
