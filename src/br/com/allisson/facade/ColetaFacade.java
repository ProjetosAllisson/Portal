package br.com.allisson.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.allisson.dao.ColetaDAO;
import br.com.allisson.modelo.User;
import br.com.allisson.modelo.coleta.Coleta;


public class ColetaFacade {

	
	private ColetaDAO coletaDao = new ColetaDAO();
	
	public List<Coleta> getColetas() throws Exception{
		coletaDao.beginTransaction();
		
		List<Coleta> result = coletaDao.findAllColeta(); 
		coletaDao.closeTransaction();
		
		return result;
		
		
		
		
	}

	public void createColeta(Coleta coleta) {
		coletaDao.beginTransaction();
		coletaDao.save(coleta);
		coletaDao.commitAndCloseTransaction();
	}
	
	public void updateColeta(Coleta coleta){
		coletaDao.beginTransaction();
		coletaDao.update(coleta);
		coletaDao.commitAndCloseTransaction();
	}
	
	public void deleteColeta(Coleta coleta){
		coletaDao.beginTransaction();
		Coleta persistedColeta = coletaDao.findReferenceOnly(coleta.getId()); 
		coletaDao.delete(persistedColeta);
		coletaDao.commitAndCloseTransaction();
	}

	public Map<Date, BigDecimal> valoresTotaisPorDate(Integer numeroDias,
			User criadoPor){
		coletaDao.beginTransaction();
		return coletaDao.valoresTotaisPorDate(15, criadoPor);
		
	}
}
