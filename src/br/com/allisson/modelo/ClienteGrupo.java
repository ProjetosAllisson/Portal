package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "STWOPETGCLI")
@NamedQueries({
	@NamedQuery(name = "ClienteGrupo.findGrupo", query = "select u from ClienteGrupo u where u.grupo =:grupo")
})
public class ClienteGrupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_GRUPO = "ClienteGrupo.findGrupo";
	
	@Id
	private Integer grupo;
	
	@OneToMany
	@JoinColumn(name="grupo")
	@OrderBy("nome")
	private List<Cliente> clientes;
	
	@Column
	private String descricao;

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}


	

}
