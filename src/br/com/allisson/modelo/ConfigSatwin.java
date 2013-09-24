package br.com.allisson.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIGURACOES")
@NamedQuery(name = "ConfigSatwin.configuracoes", query = "select u from ConfigSatwin u where u.id.modulo =:modulo and u.id.clausula =:clausula")
public class ConfigSatwin  {

	
	public static final String CONFIGURACOES = "ConfigSatwin.configuracoes";
	
	@Id
	private ConfigSatwinPk id;
	
	@Column 
	private String tpo_clausula;
	
	@Column
	private String vlr_clausula;

	public ConfigSatwinPk getId() {
		return id;
	}

	public void setId(ConfigSatwinPk id) {
		this.id = id;
	}

	public String getTpo_clausula() {
		return tpo_clausula;
	}

	public void setTpo_clausula(String tpo_clausula) {
		this.tpo_clausula = tpo_clausula;
	}

	public String getVlr_clausula() {
		return vlr_clausula;
	}

	public void setVlr_clausula(String vlr_clausula) {
		this.vlr_clausula = vlr_clausula;
	}

		
}
