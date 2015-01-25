package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import br.com.allisson.modelo.coleta.Coleta;

@Entity
@Table(name = "STWOPETCLI")
@NamedQueries({
	@NamedQuery(name = "Cliente.findCliente", query ="select u from Cliente u where u.cgc = :cgc"),
	@NamedQuery(name = "Cliente.allClientes", query ="select u from Cliente u join fetch u.grupoCliente where u.ult_acesso is not null and u.ult_acesso between :start and :end "),
	@NamedQuery(name = "Cliente.porNomeCliente", query ="select u from Cliente u where u.nome like :nome"),
	@NamedQuery(name = "Cliente.porNomeGrupo"  , query ="select u from Cliente u where u.nome like :nome and u.grupoCliente.grupo =:grupo order by u.nome"),
})

public class Cliente implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_CLIENTE = "Cliente.findCliente";
	public static final String ALL_CLIENTES = "Cliente.allClientes";
	public static final String POR_NOME_CLIENTES = "Cliente.porNomeCliente";
	public static final String POR_NOME_GRUPO = "Cliente.porNomeGrupo";
	
	@Id
	@Column(name ="cgc")
	private String cgc;
	
	//@OneToOne(mappedBy="cnpj")
	//@JoinColumn(name="cgc")
	//@OrderBy("nome")
	//private User user;
	
	@Column
	@OrderBy("nome")
	@Index(name="IDX_NOME")
	private String nome;
	
	@Column
	private String fantasia;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ult_acesso;
	
	
	@ManyToOne
	@JoinColumn(name ="grupo")
	private ClienteGrupo grupoCliente;
	
	@OneToMany(mappedBy="remetente", targetEntity = Coleta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Coleta> coletas;
	
	@OneToMany(mappedBy="destinatario", targetEntity = Coleta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Coleta> coletas_destinatario; 
	
	@Transient
	private String completeVisualizacao;
	

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

	public ClienteGrupo getGrupoCliente() {
		return grupoCliente;
	}

	public void setGrupoCliente(ClienteGrupo grupoCliente) {
		this.grupoCliente = grupoCliente;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCompleteVisualizacao() {
		if (this.cgc==null){
			return "";
		}else {
			return this.cgc+" "+this.nome;	
		}
		
	}

	public void setCompleteVisualizacao(String completeVisualizacao) {
		this.completeVisualizacao = completeVisualizacao;
	}

	public List<Coleta> getColetas_destinatario() {
		return coletas_destinatario;
	}

	public void setColetas_destinatario(List<Coleta> coletas_destinatario) {
		this.coletas_destinatario = coletas_destinatario;
	}
	
	
}
