package br.com.allisson.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {

		Connection con = new ConnectionFactory().getConnection();

		System.out.println("Conexão Aberta");

		con.close();
	}

}
