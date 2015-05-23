package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Entity
//@Table(name="TBL_DOC_EVENTO")
public class DocumentoEvento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private DocumentoEventoPK id;
	
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtEvento;
	
	@Column
	private String descricao;
	
	@ManyToOne
	private DocumentoView documento;
	
	

	public Date getDtEvento() {
		return dtEvento;
	}

	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

	public DocumentoEventoPK getId() {
		return id;
	}

	public void setId(DocumentoEventoPK id) {
		this.id = id;
	}

	
	
	

}
