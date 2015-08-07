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
@Table(name = "TBL_DUPLICATAS")

public class ExtratoDuplicata implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private ExtratoDuplicataPK id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_fatura;
	
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_cte;
	
	@Column(columnDefinition = "varchar(3)")
	private String tipo_frt;
	
	@Column
	private String notas;
	
	@Column(columnDefinition = "varchar(30)")
	private String cid_dest;
	
	@Column(columnDefinition = "varchar(2)")
	private String uf_dest;
	
	@Column(columnDefinition = "varchar(9)")
	private String ent_cep;
	
	@Column
	private BigDecimal vlr_merc;
	
	@Column
	private BigDecimal peso;
	
	@Column
	private BigDecimal peso_cub;
	
	@Column
	private BigDecimal frt_peso;
    
	@Column
	private BigDecimal advalorem;
	
	@Column
	private BigDecimal vlr_seguro;
	
	@Column
	private BigDecimal pedagio;
    
	@Column
	private BigDecimal despacho;
	
	@Column
	private BigDecimal tx_dif_acesso;
	
	@Column
	private BigDecimal outros;
    
	@Column
	private BigDecimal tot_frete;
	
	
	@Column(columnDefinition = "VARCHAR(18)")
	private String cgc_pagador;
	
	@Column
	private Integer grupo_pagador;
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExtratoDuplicata) {
			ExtratoDuplicata doc = (ExtratoDuplicata) obj;
			return doc.getId() == id;
		}

		return false;
	}
	
	public ExtratoDuplicataPK getId() {
		return id;
	}

	public void setId(ExtratoDuplicataPK id) {
		this.id = id;
	}

	public Calendar getDt_fatura() {
		return dt_fatura;
	}

	public void setDt_fatura(Calendar dt_fatura) {
		this.dt_fatura = dt_fatura;
	}

	
	public Calendar getDt_cte() {
		return dt_cte;
	}

	public void setDt_cte(Calendar dt_cte) {
		this.dt_cte = dt_cte;
	}

	public String getTipo_frt() {
		return tipo_frt;
	}

	public void setTipo_frt(String tipo_frt) {
		this.tipo_frt = tipo_frt;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getCid_dest() {
		return cid_dest;
	}

	public void setCid_dest(String cid_dest) {
		this.cid_dest = cid_dest;
	}

	public String getUf_dest() {
		return uf_dest;
	}

	public void setUf_dest(String uf_dest) {
		this.uf_dest = uf_dest;
	}

	public String getEnt_cep() {
		return ent_cep;
	}

	public void setEnt_cep(String ent_cep) {
		this.ent_cep = ent_cep;
	}

	public BigDecimal getVlr_merc() {
		return vlr_merc;
	}

	public void setVlr_merc(BigDecimal vlr_merc) {
		this.vlr_merc = vlr_merc;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPeso_cub() {
		return peso_cub;
	}

	public void setPeso_cub(BigDecimal peso_cub) {
		this.peso_cub = peso_cub;
	}

	public BigDecimal getFrt_peso() {
		return frt_peso;
	}

	public void setFrt_peso(BigDecimal frt_peso) {
		this.frt_peso = frt_peso;
	}

	public BigDecimal getAdvalorem() {
		return advalorem;
	}

	public void setAdvalorem(BigDecimal advalorem) {
		this.advalorem = advalorem;
	}

	public BigDecimal getVlr_seguro() {
		return vlr_seguro;
	}

	public void setVlr_seguro(BigDecimal vlr_seguro) {
		this.vlr_seguro = vlr_seguro;
	}

	public BigDecimal getPedagio() {
		return pedagio;
	}

	public void setPedagio(BigDecimal pedagio) {
		this.pedagio = pedagio;
	}

	public BigDecimal getDespacho() {
		return despacho;
	}

	public void setDespacho(BigDecimal despacho) {
		this.despacho = despacho;
	}

	public BigDecimal getTx_dif_acesso() {
		return tx_dif_acesso;
	}

	public void setTx_dif_acesso(BigDecimal tx_dif_acesso) {
		this.tx_dif_acesso = tx_dif_acesso;
	}

	public BigDecimal getOutros() {
		return outros;
	}

	public void setOutros(BigDecimal outros) {
		this.outros = outros;
	}

	public BigDecimal getTot_frete() {
		return tot_frete;
	}

	public void setTot_frete(BigDecimal tot_frete) {
		this.tot_frete = tot_frete;
	}

	public String getCgc_pagador() {
		return cgc_pagador;
	}

	public void setCgc_pagador(String cgc_pagador) {
		this.cgc_pagador = cgc_pagador;
	}

	public Integer getGrupo_pagador() {
		return grupo_pagador;
	}

	public void setGrupo_pagador(Integer grupo_pagador) {
		this.grupo_pagador = grupo_pagador;
	}	

}
