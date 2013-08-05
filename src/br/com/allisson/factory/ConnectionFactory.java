package br.com.allisson.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.cfg.Configuration;


public class ConnectionFactory {

	public Connection getConnection() throws SQLException {

		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}

		// return DriverManager.getConnection("jdbc:mysql://localhost/fj21",
		// "root", "");

		// File file = new File()
		//jdbc:firebirdsql:localhost/3050:C:/Portal/Base/TransMais.FDB
		String urlBd = null;// = "jdbc:firebirdsql:localhost/3050:C:/Portal/Base/TransMais.FDB";

		// Rodojumbo
		// String urlBd
		// ="jdbc:firebirdsql:192.168.0.178/3050:c:/Allisson/BaseDados/RODOJUMBO.FDB";
		                                         
		// Eureka
		// String urlBd
		// ="jdbc:firebirdsql:192.168.0.101/3050:C:/Allisson/BaseDados/eureka.FDB";

		// Up
		// String urlBd
		// ="jdbc:firebirdsql:localhost/3050:E:/Allisson/BaseDados/UPTRANSPORTES.FDB";

		// RodoExpress
		// String urlBd
		// ="jdbc:firebirdsql:192.168.0.1/3050:H:/RODOEXPRESS/ALLISSON/BASEDADOS/DRA.FDB";

		// S.D.L

	
		
		
		//File file = new File("C:/Portal/Portal v.5/portal/src/conexao.properties");
		
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("conexao.properties"); 
		Properties prop = new Properties();
				
		try{
			prop.load(is);
			
			System.out.println(prop.getProperty("conexao.url"));
			urlBd = prop.getProperty("conexao.url");
			
		}catch (IOException ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		
		Configuration cfg = new Configuration();
		

		cfg.setProperty("javax.persistence.jdbc.url",
				urlBd);

		
		EntityManagerFactory factory = Persistence.
		          createEntityManagerFactory("Portal");

		    factory.close();
		    
		    
		   
		
		
		String usuarioBd = "sysdba";
		String senhaBd = "masterkey";
		return DriverManager.getConnection(
		// "jdbc:firebirdsql:192.168.0.2:F:/Fontes/Allisson/OS_Allisson/BASEDADOS/IB_SATWIN.FDB",
				urlBd, usuarioBd, senhaBd);

		
		
	}
}
