package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.Date;

public class FiltroDocumento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Date dataInicio;
	private Date dataTermino;
	
	private Integer nota_fiscal;

	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}
	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}
	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}
	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}
	public boolean isAscendente() {
		return ascendente;
	}
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}
	public Integer getNota_fiscal() {
		return nota_fiscal;
	}
	public void setNota_fiscal(Integer nota_fiscal) {
		this.nota_fiscal = nota_fiscal;
	}


}
