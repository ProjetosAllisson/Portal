package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.ServicosDisponiveis;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;

public class TesteHibernate {
	
	public static void main(String args[]){
		
		
		
		ClienteFacade clientefacade = new ClienteFacade();
		
		Cliente cliente = clientefacade.localiza("00.000.000/0000-04");
		
		System.out.println(cliente.getNome());
		
		
		
		
		/*
		
		
		User user = new User();
		
		Cliente cliente = new Cliente();
		
		user.setLogin("felipeho");
		user.setSenha("senha");
		//user.setCnpj("328.403.108-13");
//		user.setOperador("operador");
		
		
		cliente.setCgc("328.403.108-13");
		
		cliente.setUser(user);
		user.setCliente(cliente);
		
		UserFacade userFacade = new UserFacade();
		userFacade.createUser(user);
		
		
		
		
		
		/*
		
		
		Criptografia criptografia = new Criptografia();
		
		//System.out.println(criptografia.md5("SIM"));
		
		String crip;
		crip = criptografia.md5("SIM");
		
		//List<ServicosDisponiveis> servicos = new ArrayList<ServicosDisponiveis>();
		
		ServicosDisponiveis servicos = new ServicosDisponiveis();
		servicos.setComprovanteEntrega("NAO");
		servicos.setRastreamento("SIM");
		
		ServicosDisponiveisFacade servicosFacade = new ServicosDisponiveisFacade();
		servicosFacade.createServicos(servicos);
		
	
		//servicos = servicosFacade.servicos();
	
		
		//ServicosDisponiveis servico = new ServicosDisponiveis();
		
		//servico = servicosFacade.servico();
		//System.out.println(servico.getComprovanteEntrega());
		
		//for (ServicosDisponiveis elemento: servicos){
			//System.out.println(elemento.getComprovanteEntrega());
	//	}
	 * 
	 * 
	 */
		
	}

}
