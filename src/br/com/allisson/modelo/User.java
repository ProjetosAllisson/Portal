package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.allisson.modelo.coleta.Coleta;
import br.com.allisson.util.Criptografia;

@Entity
@Table(name = "TBL_USUARIO")
@NamedQueries({
		@NamedQuery(name = "User.findUser", query = "select u from User u where u.login = :login"),
		@NamedQuery(name = "User.findAllNaoAutorizados", query = "select u from User u join fetch u.cliente left join fetch u.acessos  where u.acesso_autorizado = false"),
		@NamedQuery(name = "User.findAll", query = "select distinct u from User u join fetch u.cliente left join fetch u.acessos order by u.id")})
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_USER_LOGIN = "User.findUser";
	public static final String FIND_ALL_USER_NAO_AUTORIZADOS = "User.findAllNaoAutorizados";
	public static final String FIND_ALL = "User.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true, length = 80)
	private String login;

	@Column
	private String senha;

	// private String cnpj;

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Boolean acesso_autorizado;

	@Transient
	private String criptografado;

	@Transient
	private Criptografia criptografia = new Criptografia();

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cnpj",updatable=true)
	@OrderBy("nome")
	private Cliente cliente;

	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JoinColumn(name = "cod_usuario",updatable=true)
	@OrderBy("dt_acesso desc")
	private List<Acessos> acessos;
	
	@OneToMany(mappedBy="user", targetEntity = Coleta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("emissao desc")
	private List<Coleta> coletas;

	private Boolean grupoClientes;

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
	 * public String getCnpj() { return cnpj; }
	 * 
	 * public void setCnpj(String cnpj) { this.cnpj = cnpj; }
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
		System.out.println(cliente.getNome()+cliente.getCgc());
		this.cliente = cliente;
	}

	public List<Acessos> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acessos> acessos) {
		this.acessos = acessos;
	}

	public int totalAcessos() {
		return this.acessos.size();
	}

	public String userAcessoAutorizado() {
		if (this.getAcesso_autorizado() == true) {
			return "SIM";
		} else
			return "NÃO";
	}

	public Role getRole() {
		//if(role==null){
			//return Role.USER;
		//}
		return role;
	}

	public void setRole(Role role) {
		//if (role == null){
			//this.role = Role.USER;
		//}else{
			this.role = role;	
		//}
		

	}

	public User DevolveUsuarioSessao() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) ctx.getExternalContext()
				.getSession(false);

		return (User) session.getAttribute("usuarioAutenticado");

	}

	public Boolean getGrupoClientes() {
		return grupoClientes;
	}

	public void setGrupoClientes(Boolean grupoClientes) {
		this.grupoClientes = grupoClientes;
	}

	public Boolean consultaPorGrupoCliente() {

		if (this.grupoClientes == null) {
			return false;
		} else {
			return (this.grupoClientes == true)
					&& (this.cliente.getGrupoCliente() != null);
		}
	}

	public List<Coleta> getColetas() {
		return coletas;
	}

	public void setColetas(List<Coleta> coletas) {
		this.coletas = coletas;
	}

	
	
	
}
