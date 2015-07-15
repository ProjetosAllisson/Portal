package br.com.allisson.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "TBL_DOCUMENTO")
/*
@NamedQueries({ 
	@NamedQuery(name = "Documento.notasEmAberto", query = "select d from DocumentoView d "
														+ "where d.emissao >=:dt_partida "
														+ "and (d.cgc_remetente =:cgc or d.cgc_destinatario =:cgc or d.cgc_pagador =:cgc)"), 
	
	@NamedQuery(name = "Documento.notasEmAbertoGrupo", query = "select d from DocumentoView d "
															 + "where d.emissao >=:dt_partida "
															 + "and (d.grupo_remetente =:grupo or d.grupo_destinatario =:grupo)")
})*/
public class DocumentoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//public static final String NOTAS_EM_ABERTO = "Documento.notasEmAberto";
	//public static final String NOTAS_EM_ABERTO_GRUPO ="Documento.notasEmAbertoGrupo"; 

	@Id
	private DocumentoViewPK id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar emissao;

	@Column(columnDefinition = "VARCHAR(18)")
	private String cgc_remetente;

	@Column(columnDefinition = "VARCHAR(80)")
	private String nome_remetente;

	@Column(columnDefinition = "VARCHAR(18)")
	private String cgc_destinatario;

	@Column(columnDefinition = "VARCHAR(80)")
	private String nome_destinatario;

	@Column(columnDefinition = "VARCHAR(18)")
	private String cgc_pagador;

	@Column(columnDefinition = "VARCHAR(80)")
	private String nome_pagador;

	//@Column
	//private Integer n_fiscal;

	@Column
	private Integer ctrc;	
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar embarque;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar chegada;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar previsao_entrega;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar saida_entrega;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar entrega;

	@Column
	private String recebedor;
	
	@Column
	private Integer grupo_remetente;
	
	@Column
	private Integer grupo_destinatario;
	
	@Column
	private byte[] img_comprovante_frente;
	
	@Column
	private byte[] img_comprovante_verso;
	
	@Column
	private String comprovante_path;
	
	@Column
	private String comprovante_nome;
	
    @Column
    private String comprovante_extensao;
    
    @Column
    private String comprovante_link;
    
	@Column
	private String cte_xml_path;
	
	@Column
	private String cte_xml_nome;
	
	@Column
	private String cte_dacte_path;
	
	@Column
	private String cte_dacte_nome;
	
	@Column
	private String desc_ocorrencia;
	
	@Column 
	private BigDecimal totalFrete;
	
	//@OneToMany(mappedBy="documento",targetEntity = DocumentoEvento.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//private List<DocumentoEvento> eventos; 


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentoView) {
			DocumentoView doc = (DocumentoView) obj;
			return doc.getId() == id;
		}

		return false;
	}

	public DocumentoViewPK getId() {
		return id;
	}

	public void setId(DocumentoViewPK id) {
		this.id = id;
	}

	public Calendar getEmissao() {
		return emissao;
	}

	public void setEmissao(Calendar emissao) {
		this.emissao = emissao;
	}

	public String getCgc_remetente() {
		return cgc_remetente;
	}

	public void setCgc_remetente(String cgc_remetente) {
		this.cgc_remetente = cgc_remetente;
	}

	public String getNome_remetente() {
		return nome_remetente;
	}

	public void setNome_remetente(String nome_remetente) {
		this.nome_remetente = nome_remetente;
	}

	public String getCgc_destinatario() {
		return cgc_destinatario;
	}

	public void setCgc_destinatario(String cgc_destinatario) {
		this.cgc_destinatario = cgc_destinatario;
	}

	public String getNome_destinatario() {
		return nome_destinatario;
	}

	public void setNome_destinatario(String nome_destinatario) {
		this.nome_destinatario = nome_destinatario;
	}

	public String getCgc_pagador() {
		return cgc_pagador;
	}

	public void setCgc_pagador(String cgc_pagador) {
		this.cgc_pagador = cgc_pagador;
	}

	public String getNome_pagador() {
		return nome_pagador;
	}

	public void setNome_pagador(String nome_pagador) {
		this.nome_pagador = nome_pagador;
	}

	//public Integer getN_fiscal() {
	//	return n_fiscal;
	//}

	//public void setN_fiscal(Integer n_fiscal) {
	//	this.n_fiscal = n_fiscal;
	//}

	public Calendar getEmbarque() {
		return embarque;
	}

	public void setEmbarque(Calendar embarque) {
		this.embarque = embarque;
	}

	public Calendar getChegada() {
		return chegada;
	}

	public void setChegada(Calendar chegada) {
		this.chegada = chegada;
	}

	public Calendar getSaida_entrega() {
		return saida_entrega;
	}

	public void setSaida_entrega(Calendar saida_entrega) {
		this.saida_entrega = saida_entrega;
	}

	public Calendar getEntrega() {
		return entrega;
	}

	public void setEntrega(Calendar entrega) {
		this.entrega = entrega;
	}

	public String getRecebedor() {
		return recebedor;
	}

	public void setRecebedor(String recebedor) {
		this.recebedor = recebedor;
	}

	public Integer getGrupo_remetente() {
		return grupo_remetente;
	}

	public void setGrupo_remetente(Integer grupo_remetente) {
		this.grupo_remetente = grupo_remetente;
	}

	public Integer getGrupo_destinatario() {
		return grupo_destinatario;
	}

	public void setGrupo_destinatario(Integer grupo_destinatario) {
		this.grupo_destinatario = grupo_destinatario;
	}

	public byte[] getImg_comprovante_frente() {
		return img_comprovante_frente;
	}

	public void setImg_comprovante_frente(byte[] img_comprovante_frente) {
		this.img_comprovante_frente = img_comprovante_frente;
	}

	public byte[] getImg_comprovante_verso() {
		return img_comprovante_verso;
	}

	public void setImg_comprovante_verso(byte[] img_comprovante_verso) {
		this.img_comprovante_verso = img_comprovante_verso;
	}

	public String getCte_xml_path() {
		return cte_xml_path;
	}

	public void setCte_xml_path(String cte_xml_path) {
		this.cte_xml_path = cte_xml_path;
	}

	public String getCte_xml_nome() {
		return cte_xml_nome;
	}

	public void setCte_xml_nome(String cte_xml_nome) {
		this.cte_xml_nome = cte_xml_nome;
	}

	public String getComprovante_path() {
		return comprovante_path;
	}

	public void setComprovante_path(String comprovante_path) {
		this.comprovante_path = comprovante_path;
	}

	public String getComprovante_nome() {
		return comprovante_nome;
	}

	public void setComprovante_nome(String comprovante_nome) {
		this.comprovante_nome = comprovante_nome;
	}

	public String getComprovante_extensao() {
		return comprovante_extensao;
	}

	public void setComprovante_extensao(String comprovante_extensao) {
		this.comprovante_extensao = comprovante_extensao;
	}

	public String getCte_dacte_path() {
		return cte_dacte_path;
	}

	public void setCte_dacte_path(String cte_dacte_path) {
		this.cte_dacte_path = cte_dacte_path;
	}

	public String getCte_dacte_nome() {
		return cte_dacte_nome;
	}

	public void setCte_dacte_nome(String cte_dacte_nome) {
		this.cte_dacte_nome = cte_dacte_nome;
	}

	public String getDesc_ocorrencia() {
		return desc_ocorrencia;
	}

	public void setDesc_ocorrencia(String desc_ocorrencia) {
		this.desc_ocorrencia = desc_ocorrencia;
	}

	public Calendar getPrevisao_entrega() {
		return previsao_entrega;
	}

	public void setPrevisao_entrega(Calendar previsao_entrega) {
		this.previsao_entrega = previsao_entrega;
	}

	public String getComprovante_link() {
		return comprovante_link;
	}

	public void setComprovante_link(String comprovante_link) {
		this.comprovante_link = comprovante_link;
	}

	public BigDecimal getTotalFrete() {
		return totalFrete;
	}

	public void setTotalFrete(BigDecimal totalFrete) {
		this.totalFrete = totalFrete;
	}

	public Integer getCtrc() {
		return ctrc;
	}

	public void setCtrc(Integer ctrc) {
		this.ctrc = ctrc;
	}


	

	


}
