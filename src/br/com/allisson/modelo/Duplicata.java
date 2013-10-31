package br.com.allisson.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "STWFATTCOB")
@NamedQueries({ @NamedQuery(name = "Duplicata.EmAberto", query = "select u from Duplicata u where u.cliente.cgc =:cgc and u.dt_pagto is null and u.status = 'DG' "
		+ "order by u.dt_vencto"), 
				@NamedQuery(name = "Duplicata.EmAbertoGrupo", query ="select u from Duplicata u where u.cliente.grupoCliente.grupo =:grupo and u.dt_pagto is null and u.status = 'DG' "
		+ "order by u.dt_vencto"),})
public class Duplicata {

	public static final String EM_ABERTO = "Duplicata.EmAberto";
	public static final String EM_ABERTO_GRUPO ="Duplicata.EmAbertoGrupo";

	@Id
	private DuplicataPk id;

	@Column
	private BigDecimal tot_fatura;

	@Column
	private BigDecimal vlr_desc;

	@Column
	private BigDecimal vlr_juros;

	@Column
	private BigDecimal vlr_atraso;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_emissao;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_vencto;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_pagto;

	@ManyToOne
	@JoinColumn(name = "cgc")
	private Cliente cliente;

	@Transient
	private boolean boletoGerado;
	
	@Column
	private String status;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			Duplicata duplicata = (Duplicata) obj;
			return duplicata.getId() == getId();
		}
		return false;
	}

	public BigDecimal getTot_fatura() {
		return tot_fatura;
	}

	public void setTot_fatura(BigDecimal tot_fatura) {
		this.tot_fatura = tot_fatura;
	}

	public Calendar getDt_emissao() {
		return dt_emissao;
	}

	public void setDt_emissao(Calendar dt_emissao) {
		this.dt_emissao = dt_emissao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DuplicataPk getId() {
		return id;
	}

	public void setId(DuplicataPk id) {
		this.id = id;
	}

	public boolean isBoletoGerado() {
		return boletoGerado;
	}

	public void setBoletoGerado(boolean boletoGerado) {
		this.boletoGerado = boletoGerado;
	}

	public Date getDt_emissaoFormatada() {
		return this.dt_emissao.getTime();
	}

	public Date getDt_venctoFormatada() {
		return this.dt_vencto.getTime();
	}

	public Date getDt_pagtoFormatada() {
		return this.dt_pagto.getTime();
	}

	public BigDecimal getVlr_desc() {
		if (vlr_desc == null){
			return new BigDecimal(0);
		}
		return vlr_desc;
	}

	public void setVlr_desc(BigDecimal vlr_desc) {
		this.vlr_desc = vlr_desc;
		
	}

	public BigDecimal getVlr_juros() {
		if (vlr_juros == null){
			return new BigDecimal(0);
		}
		return vlr_juros;
	}

	public void setVlr_juros(BigDecimal vlr_juros) {
		this.vlr_juros = vlr_juros;
		
	}

	public BigDecimal getVlr_atraso() {
		return vlr_atraso;
	}

	public void setVlr_atraso(BigDecimal vlr_atraso) {
		this.vlr_atraso = vlr_atraso;
	}

	public BigDecimal getSaldoPagar() {
		return getTot_fatura().add(getVlr_juros()).subtract(getVlr_desc());
	}
}
