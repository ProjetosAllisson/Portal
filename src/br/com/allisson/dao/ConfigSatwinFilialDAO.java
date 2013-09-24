package br.com.allisson.dao;


import java.util.HashMap;
import java.util.Map;

import br.com.allisson.modelo.ConfigSatwinFilial;

public class ConfigSatwinFilialDAO extends GenericDAO<ConfigSatwinFilial>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigSatwinFilialDAO(){
		super(ConfigSatwinFilial.class);
	}
	
	public ConfigSatwinFilial configSatwinFilial(String modulo, String clausula, String filial){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("modulo", modulo);
		parameters.put("clausula", clausula);
		parameters.put("filial", filial);
		
		return super.findOneResult(ConfigSatwinFilial.CONFIGURACOES_FILIAL, parameters);
	}

}
