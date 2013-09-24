package br.com.allisson.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG_FILIAL")
@NamedQuery(name = "ConfigSatwinFilial.configuracoesFilial", query = "select u from ConfigSatwinFilial u where u.id.modulo =:modulo and u.id.clausula =:clausula " +
		" and u.id.filial =:filial")
public class ConfigSatwinFilial {

	
	public static final String CONFIGURACOES_FILIAL = "ConfigSatwinFilial.configuracoesFilial"; 
	
	@Id
	private ConfigSatwinFilialPk id;
	
	private String tpo_clausula;
	private String vlr_clausula;
	
	public ConfigSatwinFilialPk getId() {
		return id;
	}
	public void setId(ConfigSatwinFilialPk id) {
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
