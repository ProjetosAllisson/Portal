package br.com.allisson.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.allisson.modelo.ConfigSatwin;

public class ConfigSatwinDAO extends GenericDAO<ConfigSatwin>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigSatwinDAO(){
		super(ConfigSatwin.class);
	}
	
	public ConfigSatwin configSatwin(String modulo, String clausula){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("modulo", modulo);
		parameters.put("clausula", clausula);
		
		return super.findOneResult(ConfigSatwin.CONFIGURACOES, parameters);
	}

}
