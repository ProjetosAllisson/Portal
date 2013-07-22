package br.com.allisson.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "STWOPETCLI")
@NamedQueries({
	@NamedQuery(name = "Cliente.findCliente", query ="select u from Cliente u where u.cgc = :cgc"),
	@NamedQuery(name = "Cliente.allClientes", query ="select u from Cliente u"),
})

public class Cliente {

	
	public static final String FIND_CLIENTE = "Cliente.findCliente";
	
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
