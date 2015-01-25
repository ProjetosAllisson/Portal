package br.com.allisson.modelo.coleta;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class VeiculoPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String placa;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
