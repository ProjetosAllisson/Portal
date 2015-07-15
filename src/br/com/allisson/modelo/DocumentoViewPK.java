package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DocumentoViewPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String documento;
	private String n_fiscal;


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public String getN_fiscal() {
		return n_fiscal;
	}


	public void setN_fiscal(String n_fiscal) {
		this.n_fiscal = n_fiscal;
	}


	

}
