package br.com.allisson.bean;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.User;

@ManagedBean(name = "usuariosBean")
@SessionScoped
public class UsuariosBean extends AbstractMB {

	private User usuario;
	private UserFacade userFacade;
	
	private User usuarioSelecionado;
	private User[] usuariosSelecionados;
	private List<User> users;
	private List<User> usersNaoAutorizados;

	private String cnpj;
	

	private Cliente cliente;

	public UsuariosBean() {
		resetUser();
	}

	public void InserirUsuario() {
	

		Cliente cliente = new Cliente();
		cliente.setCgc(this.cnpj);
		usuario.setCliente(cliente);

		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			displayErrorMessageToUser("Registro Duplicado. O Usu�rio que voc� esta inserindo ja existe");
		} else {
			usuario.setAcesso_autorizado(false);
			getUserFacade().createUser(usuario);

			loadUsers();
			
			displayInfoMessageToUser("Usu�rio inserido com sucesso");

			//return "usuario_sucesso";
		}
		//return null;

		/*
		 * try { usuarioDao.adiciona(this.usuario); return "usuario_sucesso"; }
		 * catch (Exception e) { FacesMessage msg = new
		 * FacesMessage("Registro Duplicado",
		 * "O Usu�rio que voc� esta inserindo ja existe");
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
				.put("usuarioBean", new UsuariosBean());
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("usuario.jsf");

	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
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

	public List<User> getAllUsers() {
		if (users == null) {
			loadUsers();
		}
		return users;
	}
	
	public List<User> getAllUsersNaoAutorizados() {
		loadUsersNaoAutorizados();
		
		return usersNaoAutorizados;
	}

	private void loadUsersNaoAutorizados() {
		usersNaoAutorizados = getUserFacade().listAllNaoAutorizados();		
	}

	private void loadUsers() {
		users = getUserFacade().listAll();

	}

	public void usuarioSelecionado(User usuarioSelecionado) {
		this.setUsuarioSelecionado(usuarioSelecionado);
	}

	public void resetUser() {
		usuario = new User();
	}

	
	public void deleteUser() {
		try {
			System.out.println("Usuario" + usuarioSelecionado.getLogin());
			getUserFacade().deleteUser(usuarioSelecionado);
			closeDialog();
			displayInfoMessageToUser("Exclu�do com sucesso");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateUser() {
		try {
			getUserFacade().updateUser(usuarioSelecionado);
			closeDialog();
			displayInfoMessageToUser("Alterado com Sucesso");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public User getUsuarioSelecionado() {
		if (usuarioSelecionado == null) {
			return new User();
		}
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(User usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getCnpjInclusao() {				
		return cnpj;
	}
	
	public String getCnpj() {

		System.out.println("cnpj get");
		if (this.usuarioSelecionado != null) {
			System.out.println("Usuario selecionado "
					+ this.usuarioSelecionado.getCliente().getCgc());
			
			return this.usuarioSelecionado.getCliente().getCgc();
			
		}
		
		return cnpj;
		
	}

	public void setCnpj(String cnpj) {
		this.usuarioSelecionado.getCliente().setCgc(cnpj);
	}
	
	public void setCnpjInclusao(String cnpj) {
		this.cnpj = cnpj;
	}

	public User[] getUsuariosSelecionados() {
		return usuariosSelecionados;
	}

	public void setUsuariosSelecionados(User[] usuariosSelecionados) {
		this.usuariosSelecionados = usuariosSelecionados;
	}

	
	public void autorizaUsuarios(){
		for (User usuario :getUsuariosSelecionados()){
			usuario.setAcesso_autorizado(true);
			getUserFacade().updateUser(usuario);
		}
		loadUsersNaoAutorizados();
	}


		


}
