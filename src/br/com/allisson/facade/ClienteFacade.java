package br.com.allisson.facade;

import java.util.List;

import br.com.allisson.dao.ClienteDAO;
import br.com.allisson.modelo.Cliente;

public class ClienteFacade {
	
	private ClienteDAO clienteDAO = new ClienteDAO();
	
	public Cliente localiza(String cnpj){
		clienteDAO.beginTransaction();
		
		Cliente cliente = clienteDAO.findCliente(cnpj);
		
		System.out.println(cliente.getNome());
		
		return cliente;
		
	}
	
	public List<Cliente> allClientes(){
		clienteDAO.beginTransaction();
		
		List<Cliente> clientes = clienteDAO.allClientes();
		
		//Collections.sort(clientes,"nome");

		
		return clientes;
	}
	
	public List<Cliente> allClientesPorNome(String nome){
		clienteDAO.beginTransaction();
		
		List<Cliente> result = clienteDAO.allClientesPorNome(nome);
		
		clienteDAO.closeTransaction();
		
		return result;
	}


}
