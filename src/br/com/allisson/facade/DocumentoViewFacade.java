package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.DocumentoViewDAO;
import br.com.allisson.modelo.DocumentoView;
import br.com.allisson.modelo.FiltroDocumento;

public class DocumentoViewFacade {
	
	private DocumentoViewDAO docViewDao = new DocumentoViewDAO();
	
	public List<DocumentoView> consultaDocumentos(FiltroDocumento filtros){
		List<DocumentoView> resultado = new ArrayList<DocumentoView>();
		
		docViewDao.beginTransaction();
		resultado.addAll(docViewDao.filtrados(filtros));
		docViewDao.closeTransaction();
		
		return resultado;
	}

}
