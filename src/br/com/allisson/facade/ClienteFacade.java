package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.ClienteDAO;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.Role;
import br.com.allisson.modelo.User;

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
		
		User usuario = new User();
		usuario = usuario.DevolveUsuarioSessao();
		
		List<Cliente> result = new ArrayList<Cliente>();
		
		if (usuario == null){
			return result;
		}
		
		if ((usuario.getRole().equals(Role.USER)) && (usuario.getCliente().getGrupoCliente()!=null)) {
			result = clienteDAO.allClientesPorNome(nome.toUpperCase(),usuario.getCliente().getGrupoCliente().getGrupo());	
		}else {
			result = clienteDAO.allClientesPorNome(nome.toUpperCase(),0);
		}
		
		
		clienteDAO.closeTransaction();
		
		
		return result;
	}


}
