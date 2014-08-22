package br.com.allisson.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;

@ManagedBean(name="autorizarAcessoURLBean")
@RequestScoped
public class AutorizarAcessoURLBean {
	
	
	private String parametroUser;
	
	private User usuario = new User();
	private UserFacade userFacade = new UserFacade();
	
	private boolean autorizado;
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getParametroUser() {
		return parametroUser;
	}

	public void setParametroUser(String parametroUser) {
		this.parametroUser = parametroUser;

		int id;

		System.out.println(Criptografia.descriptografa(parametroUser, 25));

		id = Integer.parseInt(Criptografia.descriptografa(parametroUser, 25));

		this.usuario = userFacade.findUser(id);
		
		
		if (this.usuario !=null){
			userFacade.autorizaUsuario(usuario);
			setAutorizado(true);
		}

	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}


	

}
