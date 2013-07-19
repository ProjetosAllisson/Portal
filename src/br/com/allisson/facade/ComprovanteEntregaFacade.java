package br.com.allisson.facade;

import java.sql.SQLException;

import br.com.allisson.dao.ComprovanteEntregaDAO;
import br.com.allisson.modelo.ComprovanteEntrega;
import br.com.allisson.modelo.Documento;

public class ComprovanteEntregaFacade {

	private ComprovanteEntregaDAO comprovanteDao = new ComprovanteEntregaDAO();
	
	public ComprovanteEntrega getComprovante(Documento documento) throws SQLException{
		ComprovanteEntrega comprovanteEntrega = new ComprovanteEntrega();
		
		comprovanteEntrega = comprovanteDao.getComprovante(documento);
		
		return comprovanteEntrega;
	}
}
