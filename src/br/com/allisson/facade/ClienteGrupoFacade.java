package br.com.allisson.facade;

import br.com.allisson.dao.ClienteGrupoDAO;
import br.com.allisson.modelo.ClienteGrupo;

public class ClienteGrupoFacade {
	
	private ClienteGrupoDAO grupoDao = new ClienteGrupoDAO();
	
	
	public ClienteGrupo findGrupo(int grupo){
		grupoDao.beginTransaction();
		
		ClienteGrupo grp = grupoDao.findGrupo(grupo);
		
		return grp;
	}
	
	
	

}
