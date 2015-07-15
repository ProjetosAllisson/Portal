package br.com.allisson.modelo.coleta.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.allisson.modelo.User;

public class UserValor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User usuario;
	private BigDecimal valor;
	
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
