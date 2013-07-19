package br.com.allisson.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "STWOPETCLI")
@NamedQuery(name = "Cliente.findCliente", query ="select u from Cliente u where u.cgc = :cgc")
public class Cliente {

	
	public static final String FIND_CLIENTE = "Cliente.findCliente";
	
	@Id
	@Column(name ="cgc")
	private String cgc;
	
	//@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	//@JoinColumn(name="cgc")
	//private User user;
	
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
