package br.com.allisson.facade;

import br.com.allisson.dao.ClienteDAO;
import br.com.allisson.modelo.Cliente;

public class ClienteFacade {
	
	private ClienteDAO clienteDAO = new ClienteDAO();
	
	public Cliente localiza(String cnpj){
		clienteDAO.beginTransaction();
		
		Cliente cliente = clienteDAO.findCliente(cnpj);
		
		return cliente;
		
	}

}
