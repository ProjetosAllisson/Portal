package br.com.allisson.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.User;


@SessionScoped
@ManagedBean(name ="userBean")
public class UserBean extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String INJECTION_NAME = "#{userBean}";

	
	private User user;
	private User usuarioSelecionado;
	private UserFacade userFacade;
	private List<User> users;
	 
	
	public UserBean(){
		resetUser();
	}

	public boolean isAdmin() {
		return user.isAdmin();
	}

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	
	public void resetUser() {
		user = new User();
	}
		
	
	public User getUser() {
		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public UserFacade getUserFacade(){
		if (userFacade == null) {
			userFacade = new UserFacade();
		}
		
		return userFacade;
	}
	
	public List<User> getAllUsers(){
		if (users == null){
			loadUsers();
		}
		return users;
	}

	private void loadUsers() {
		users = getUserFacade().listAll();
		
	}
	
	
	public void usuarioSelecionado(User usuarioSelecionado){
		this.setUsuarioSelecionado(usuarioSelecionado);
	}
	
	public void deleteUser() {
		try {
			System.out.println("Usuario"+usuarioSelecionado.getLogin());
			getUserFacade().deleteUser(usuarioSelecionado);
			closeDialog();
			displayInfoMessageToUser("Excluído com sucesso");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void updateUser(){
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
	
	public int qtdeAcessos(){
		return this.user.getAcessos().size();
	}

	
}
