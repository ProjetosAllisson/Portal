package br.com.allisson.facade;

import br.com.allisson.dao.ServicosDisponiveisDAO;
import br.com.allisson.modelo.ServicosDisponiveis;

public class ServicosDisponiveisFacade {

	private ServicosDisponiveisDAO servDisponiveisDAO = new ServicosDisponiveisDAO();
	
	public void createServicos(ServicosDisponiveis servDisponiveis){
		servDisponiveisDAO.beginTransaction();
		servDisponiveisDAO.save(servDisponiveis);
		servDisponiveisDAO.commitAndCloseTransaction();
		
	}
	
	/*
	public List<ServicosDisponiveis> servicos(){
		servDisponiveisDAO.beginTransaction();
		List<ServicosDisponiveis> result = servDisponiveisDAO.servicos();
		servDisponiveisDAO.closeTransaction();
		return result;
	}*/
	
	
	public ServicosDisponiveis servico(){
		servDisponiveisDAO.beginTransaction();
		ServicosDisponiveis result = servDisponiveisDAO.servico();
		servDisponiveisDAO.closeTransaction();
		return result;
	}
	
}
