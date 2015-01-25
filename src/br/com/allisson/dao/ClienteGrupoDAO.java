package br.com.allisson.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.allisson.modelo.ClienteGrupo;

public class ClienteGrupoDAO extends GenericDAO<ClienteGrupo>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClienteGrupoDAO(){
		super(ClienteGrupo.class);
	}


	public ClienteGrupo findGrupo(int grupo){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("grupo", grupo);
	
		return super.findOneResult(ClienteGrupo.FIND_GRUPO, parameters);

	}

	
}
