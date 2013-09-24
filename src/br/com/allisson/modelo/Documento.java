package br.com.allisson.modelo;

import java.util.Date;


public class Documento {
	
	private String documento;
	private String doc;
	private String fil_orig;
	private Integer nr_cto;
	private String cgc;
	private String serie;
	private Long nota;
	private Integer indicador;
	private Date emissao;
	private Date embarque;
	private Date chegada;
	private Date entrega;
	private String remetente;
	private String destinatario;
	private String ocorrencia;
	private HistoricoNf historicoNf;
	private Date saida;
	private Boolean temImagem;
	private String recebedor;
    
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public Date getEmbarque() {
		return embarque;
	}
	public void setEmbarque(Date embarque) {
		this.embarque = embarque;
	}
	public Date getChegada() {
		return chegada;
	}
	public void setChegada(Date chegada) {
		this.chegada = chegada;
	}
	public Date getEntrega() {
		return entrega;
	}
	public void setEntrega(Date entrega) {
		this.entrega = entrega;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remtente) {
		this.remetente = remtente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Long getNota() {
		return nota;
	}
	public void setNota(Long nota) {
		this.nota = nota;
	}
	public String getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public HistoricoNf getHistoricoNf() {
		return historicoNf;
	}
	public void setHistoricoNf(HistoricoNf historicoNf) {
		this.historicoNf = historicoNf;
	}
	public String getCgc() {
		return cgc;
	}
	public void setCgc(String cgc) {
		this.cgc = cgc;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public Integer getIndicador() {
		return indicador;
	}
	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}
	public Date getSaida() {
		return saida;
	}
	public void setSaida(Date saida) {
		this.saida = saida;
	}

	/*
	public byte[] getComprovanteImagem() {
		return comprovanteImagem;
	}
	public void setComprovanteImagem(byte[] comprovanteImagem) {
		this.comprovanteImagem = comprovanteImagem;
	}
	public StreamedContent getImagem() {
		imagem = new DefaultStreamedContent(new ByteArrayInputStream(this.getComprovanteImagem()));
				
		return imagem;
	}
	
	*/
	public Integer getNr_cto() {
		return nr_cto;
	}
	public void setNr_cto(Integer nr_cto) {
		this.nr_cto = nr_cto;
	}
	public String getFil_orig() {
		return fil_orig;
	}
	public void setFil_orig(String fil_orig) {
		this.fil_orig = fil_orig;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public Boolean getTemImagem() {
		return temImagem;
	}
	public void setTemImagem(Boolean temImagem) {
		this.temImagem = temImagem;
	}
	public String getRecebedor() {
		return recebedor;
	}
	public void setRecebedor(String recebedor) {
		this.recebedor = recebedor;
	}
	
	
	

}
