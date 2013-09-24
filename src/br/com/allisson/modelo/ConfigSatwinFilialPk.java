package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ConfigSatwinFilialPk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String modulo;
	private String clausula;
	private String filial;
	//@ManyToOne
	//private Filial filial;

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getClausula() {
		return clausula;
	}

	public void setClausula(String clausula) {
		this.clausula = clausula;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	
}
