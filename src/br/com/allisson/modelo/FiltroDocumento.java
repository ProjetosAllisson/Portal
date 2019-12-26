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
	
	private Integer ctrc;
	private String nota_fiscal;
	private String chave_acesso;
	
	private Cliente clienteSelecionado = new Cliente();
	private String cnpj_cpf;
	private boolean pesquisaAdmin = false;

	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	private boolean consultaPublica = false;
	
	
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
	public String getNota_fiscal() {
		return nota_fiscal;
	}
	public void setNota_fiscal(String nota_fiscal) {
		this.nota_fiscal = nota_fiscal;
	}
	public Integer getCtrc() {
		return ctrc;
	}
	public void setCtrc(Integer ctrc) {
		this.ctrc = ctrc;
	}
	public String getCnpj_cpf() {
		return cnpj_cpf;
	}
	public void setCnpj_cpf(String cnpj_cpf) {
		this.cnpj_cpf = cnpj_cpf;
	}
	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}
	
	
	
	public boolean isConsultaPublica() {
		return consultaPublica;
	}
	public void setConsultaPublica(boolean consultaPublica) {
		this.consultaPublica = consultaPublica;
	}
	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
		
		this.cnpj_cpf = "";
		
		if (this.clienteSelecionado !=null){
			if (!this.clienteSelecionado.getCgc().equals("")){
				this.cnpj_cpf = this.clienteSelecionado.getCgc();
			}
		}
	}
	public boolean isPesquisaAdmin() {
		return pesquisaAdmin;
	}
	public void setPesquisaAdmin(boolean pesquisaAdmin) {
		this.pesquisaAdmin = pesquisaAdmin;
	}
	public String getChave_acesso() {
		return chave_acesso;
	}
	public void setChave_acesso(String chave_acesso) {
		this.chave_acesso = chave_acesso;
	}


}
