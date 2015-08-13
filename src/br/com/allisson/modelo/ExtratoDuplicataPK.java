package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ExtratoDuplicataPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int nr_cto;
	
	
	
	public int getNr_cto() {
		return nr_cto;
	}
	public void setNr_cto(int nr_cto) {
		this.nr_cto = nr_cto;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
