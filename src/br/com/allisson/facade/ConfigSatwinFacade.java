package br.com.allisson.facade;

import br.com.allisson.dao.ConfigSatwinDAO;
import br.com.allisson.dao.ConfigSatwinFilialDAO;
import br.com.allisson.modelo.ConfigSatwin;
import br.com.allisson.modelo.ConfigSatwinFilial;

public class ConfigSatwinFacade {
	
	private ConfigSatwinDAO configSatwinDAO = new ConfigSatwinDAO();
	private ConfigSatwinFilialDAO configSatwinFilialDAO = new ConfigSatwinFilialDAO();
	
	public String leclausula(String modulo,String clausula,String filial){
		
		configSatwinFilialDAO.beginTransaction();
		ConfigSatwinFilial resultSatwinFilial = configSatwinFilialDAO.configSatwinFilial(modulo, clausula, filial); 
		configSatwinFilialDAO.closeTransaction();
		
		if (resultSatwinFilial != null) {
			return resultSatwinFilial.getVlr_clausula();
		}
		else{
			configSatwinDAO.beginTransaction();
			ConfigSatwin resultSatwin = configSatwinDAO.configSatwin(modulo, clausula); 
			configSatwinDAO.closeTransaction();
			return resultSatwin.getVlr_clausula();		
		}
		
	}
	
}
