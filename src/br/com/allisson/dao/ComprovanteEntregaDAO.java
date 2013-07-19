package br.com.allisson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.allisson.factory.ConnectionFactory;
import br.com.allisson.modelo.ComprovanteEntrega;
import br.com.allisson.modelo.Documento;

public class ComprovanteEntregaDAO {

	private final Connection connection;
	
	public ComprovanteEntregaDAO(){
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ComprovanteEntrega getComprovante(Documento documento) throws SQLException{
		
		String sql = "select documento, fil_orig, nr_cto, imagem, verso from stwopetdig " +
				     "where documento = ? and fil_orig = ? and nr_cto = ? ";
		
		PreparedStatement stm = this.connection.prepareStatement(sql);
		
		stm.setString(1,documento.getDoc());
		stm.setString(2, documento.getFil_orig());
		stm.setInt(3, documento.getNr_cto());
		
		ResultSet rs = stm.executeQuery();
		
		ComprovanteEntrega comprovante = new ComprovanteEntrega();
		
		while (rs.next()) {
			comprovante.setDocumento(rs.getString("documento"));
			comprovante.setFilial(rs.getString("fil_orig"));
			comprovante.setNrdocumento(rs.getInt("nr_cto"));
			comprovante.setFrente(rs.getBytes("imagem"));
			comprovante.setVerso(rs.getBytes("verso"));
		}
		return comprovante;
	}
	
}
