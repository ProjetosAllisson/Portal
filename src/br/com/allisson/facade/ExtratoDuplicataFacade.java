package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.ExtratoDuplicataDAO;
import br.com.allisson.modelo.ExtratoDuplicata;
import br.com.allisson.modelo.FiltroDocumento;

public class ExtratoDuplicataFacade {
	
	private ExtratoDuplicataDAO extrato = new ExtratoDuplicataDAO();
	
	public List<ExtratoDuplicata> extratoDuplicatasEmitidas(FiltroDocumento filtro ){
		List<ExtratoDuplicata> result = new ArrayList<ExtratoDuplicata>();
		
		extrato.beginTransaction();
		result.addAll(extrato.extratoDuplicatasEmitidas(filtro));
		extrato.closeTransaction();
		
		return result;
	}

}
