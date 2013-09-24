package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DuplicataPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipo_doc;
	private String fil_orig;
	private int fatura;
	private String ano;
	
	
	
	public String getTipo_doc() {
		return tipo_doc;
	}
	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}
	public String getFil_orig() {
		return fil_orig;
	}
	public void setFil_orig(String fil_orig) {
		this.fil_orig = fil_orig;
	}
	public int getFatura() {
		return fatura;
	}
	public void setFatura(int fatura) {
		this.fatura = fatura;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}

	
}
