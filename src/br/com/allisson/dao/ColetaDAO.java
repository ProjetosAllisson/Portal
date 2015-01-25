package br.com.allisson.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.allisson.modelo.User;
import br.com.allisson.modelo.coleta.Coleta;

public class ColetaDAO extends GenericDAO<Coleta>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ColetaDAO(){
		super(Coleta.class);
	}
	
	public List<Coleta> findAllColeta(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		User user = new User();
		user = user.DevolveUsuarioSessao();
		
		parameters.put("user", user);
		
		if (user.isAdmin()){
			return super.findAllResult(Coleta.FIND_ALL, null);
		}
		
		return super.findAllResult(Coleta.FIND_ALL_USER, parameters);
	}

}
