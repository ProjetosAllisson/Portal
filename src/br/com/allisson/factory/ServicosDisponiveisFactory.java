package br.com.allisson.factory;

import br.com.allisson.facade.ServicosDisponiveisFacade;
import br.com.allisson.modelo.ServicosDisponiveis;

public class ServicosDisponiveisFactory {

	public ServicosDisponiveis getServicos(){
		
		ServicosDisponiveisFacade servicosFacade = new ServicosDisponiveisFacade();
		
		return servicosFacade.servico();				
		
	}
	
}
