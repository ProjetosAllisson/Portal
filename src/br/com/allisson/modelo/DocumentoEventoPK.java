package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DocumentoEventoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String documento;
	//private String ocorrencia;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	//public String getOcorrencia() {
	//	return ocorrencia;
	//}

	//public void setOcorrencia(String ocorrencia) {
	//	this.ocorrencia = ocorrencia;
	//}

}
