package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.allisson.util.Criptografia;

@Entity
@Table(name = "TBL_USUARIO")
@NamedQueries({
	@NamedQuery(name = "User.findUser", query = "select u from User u where u.login = :login"),
	@NamedQuery(name = "User.findAllNaoAutorizados", query = "select u from User u where u.acesso_autorizado = false")
})
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_USER_LOGIN = "User.findUser";
	public static final String FIND_ALL_USER_NAO_AUTORIZADOS = "User.findAllNaoAutorizados";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true,length=80)
	private String login;
	
	@Column
	private String senha;
	
	//private String cnpj;

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Boolean acesso_autorizado;

	@Transient
	private String criptografado;

	@Transient
	private Criptografia criptografia = new Criptografia();
	
	@OneToOne
	@JoinColumn(name="cnpj")
	@OrderBy("nome")
	private Cliente cliente;
	
	
	@OneToMany
	@JoinColumn(name="cod_usuario")
	@OrderBy("dt_acesso")
	private List<Acessos> acessos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.criptografado = Criptografia.md5(senha);
		this.senha = this.criptografado;
	}

	
	
	/*
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
*/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(getRole());
	}

	public boolean isUser() {
		return Role.USER.equals(getRole());
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return user.getId() == id;
		}
		return false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getAcesso_autorizado() {
		return acesso_autorizado;

	}

	public void setAcesso_autorizado(Boolean acesso_autorizado) {
		this.acesso_autorizado = acesso_autorizado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Acessos> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acessos> acessos) {
		this.acessos = acessos;
	}
	
	public int totalAcessos(){
		return this.acessos.size();
	}
	
	public String userAcessoAutorizado(){
		if (this.getAcesso_autorizado()==true){
			return "SIM";
		}
		else
			return "NÃO";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
