package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STWOPETCLI")
@NamedQueries({
	@NamedQuery(name = "Cliente.findCliente", query ="select u from Cliente u where u.cgc = :cgc"),
	@NamedQuery(name = "Cliente.allClientes", query ="select u from Cliente u where u.ult_acesso is not null and u.ult_acesso between :start and :end "),
	@NamedQuery(name = "Cliente.porNomeCliente", query ="select u from Cliente u where u.nome like :nome"),
})

public class Cliente implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_CLIENTE = "Cliente.findCliente";
	public static final String ALL_CLIENTES = "Cliente.allClientes";
	public static final String POR_NOME_CLIENTES = "Cliente.porNomeCliente";
	
	@Id
	@Column(name ="cgc")
	private String cgc;
	
	//@OneToOne(mappedBy="cnpj")
	//@JoinColumn(name="cgc")
	//@OrderBy("nome")
	//private User user;
	
	@Column
	@OrderBy("nome")
	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ult_acesso;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

//	public User getUser() {
	//	return user;
//	}

	//public void setUser(User user) {
		//this.user = user;
	//}
	
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			Cliente cliente = (Cliente) obj;
			return cliente.getCgc() == cgc;
		}
		return false;
	}
	
	
}
