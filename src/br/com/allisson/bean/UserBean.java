package br.com.allisson.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.allisson.modelo.User;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String INJECTION_NAME = "#{userBean}";
	
	private User user;
	public UserBean() {
		resetUser();
	}

	public boolean isAdmin() {
		
		System.out.println(getUser().isAdmin());
		
		return getUser().isAdmin();
	}

	public boolean isDefaultUser() {
		System.out.println(getUser().isUser());
		
		return getUser().isUser();
	}

	
	public User getUser() {

		return user;
	}

	public void resetUser() {
		setUser(new User());
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public Boolean getConsultaporGrupo() {
		return this.user.consultaPorGrupoCliente();
	}

	

	
	
	
	
	
	
	

}
