package br.com.allisson.bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.User;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean extends AbstractMB {

	private User usuario;
	private UserFacade userFacade;

	private String cnpj;

	private Cliente cliente;

	public UsuarioBean() {
		this.usuario = new User();
	}

	public String InserirUsuario() throws SQLException {
	

		Cliente cliente = new Cliente();
		cliente.setCgc(this.cnpj);
		usuario.setCliente(cliente);

		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			displayErrorMessageToUser("Registro Duplicado. O Usuário que você esta inserindo ja existe");
		} else {
			usuario.setAcesso_autorizado(false);
			getUserFacade().createUser(usuario);

			displayInfoMessageToUser("Usuário inserido com sucesso");

			return "usuario_sucesso";
		}
		return null;

		/*
		 * try { usuarioDao.adiciona(this.usuario); return "usuario_sucesso"; }
		 * catch (Exception e) { FacesMessage msg = new
		 * FacesMessage("Registro Duplicado",
		 * "O Usuário que você esta inserindo ja existe");
		 * //FacesContext.getCurrentInstance().addMessage(null, msg);
		 * 
		 * }
		 */
		// }
		// return null;

	}

	public void cadastra() throws IOException {
		// return "usuario";

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("usuarioBean", new UsuarioBean());
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("usuario.jsf");

	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getCnpj() {

		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}
		return userFacade;
	}


	
	
	

		


}
