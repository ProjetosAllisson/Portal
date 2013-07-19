package br.com.allisson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.allisson.factory.ConnectionFactory;
import br.com.allisson.modelo.Usuario;

public class UsuarioDAO_Desuso {

	private final Connection connection;

	public UsuarioDAO_Desuso() {
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Usuario exite(String usuario, String senha) throws SQLException {

		PreparedStatement stm = this.connection
				.prepareStatement("select id,login,senha,cnpj from tbl_usuario where login = ? and senha = ?");

		stm.setString(1, usuario);
		stm.setString(2, senha);

		ResultSet rs = stm.executeQuery();


		while (rs.next()) { 
			return populaUsuario(rs);
			} return null;
		
		
		
//		rs.next();
//		return populaUsuario(rs);

	}

	public void adiciona(Usuario usuario) {
		
		try {
		PreparedStatement stm = this.connection
				.prepareStatement("insert into tbl_usuario (login, senha, cnpj) values (?,?,?)");
		
		stm.setString(1, usuario.getLogin());
		stm.setString(2, usuario.getSenha());
		stm.setString(3, usuario.getCnpj());
		
		stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Usuario populaUsuario(ResultSet rs) throws SQLException {

		Usuario usuario = new Usuario();

		usuario.setId(rs.getInt("ID"));
		usuario.setLogin(rs.getString("login"));
		usuario.setCnpj(rs.getString("cnpj"));

		return usuario;

	}
}
