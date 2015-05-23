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


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}

}
