package br.com.allisson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.allisson.factory.ConnectionFactory;
import br.com.allisson.modelo.Empresa;

public class EmpresaDAO {

	private final Connection connection;

	public EmpresaDAO() {
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Empresa DadosTransportadora(){
		
		String sql = "select first 1 nome, endereco, logo from stwopetvar";
		
		PreparedStatement stm;
				
		try {
			stm = this.connection.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			rs.next();
			
			System.out.println("Empresa DAO Ok");
			
			Empresa empresa = new Empresa();
			
						
			empresa.setNome(rs.getString("nome"));
			empresa.setEndereco(rs.getString("endereco"));
			empresa.setLogo(rs.getBytes("logo"));
			
			
			
			return empresa;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
			
		
		
	}
	/*
	private Empresa populaEmpresa(ResultSet rs){
        Empresa empresa = new Empresa();
		
		empresa.setNome(rs.getString("nome"));
		empresa.setEndereco(rs.getString("endereco"));
		
	}*/

}
