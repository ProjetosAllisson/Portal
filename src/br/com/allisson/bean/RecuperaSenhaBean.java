package br.com.allisson.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;

@ManagedBean(name="recuperaSenhaBean")
@ViewScoped
public class RecuperaSenhaBean extends AbstractMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private User usuario;
	private UserFacade userFacade;
	
	
	private String parametroUser;
	
	private String email;
	private String senha;
	
	
	public boolean isUserPorEmail(){
		return getUserFacade().userPorEmail(this.email) !=null;
	}
	
	public String recuperaSenha() {
		
		List<User> usuarios = new ArrayList<User>();
		usuarios.addAll(getUserFacade().userPorEmail(this.email));
		
		if (usuarios.size()==0) {
			displayErrorMessageToUser("E-mail não localizado em nosso cadastro.");
			return "";
		}else{
			getUserFacade().enviarEmailRecuperarSenha(usuarios);
			return "/pages/public/esqueceuSenhaRetorno.jsf?faces-redirect=true";
			
		}
	}
	
	
	public void setParametroUser(String parametroUser) {
		this.parametroUser = parametroUser;

		int id;

		System.out.println(Criptografia.descriptografa(parametroUser, 25));

		id = Integer.parseInt(Criptografia.descriptografa(parametroUser, 25));
		
		setUsuario(new User());
		this.setUsuario(getUserFacade().findUser(id));
		
		
		if (this.getUsuario() !=null){
		   
		}
		
	

	}
	
	//http://localhost:8080/portal/pages/public/novaSenha.jsf?903DAEF4110B5F2D2F0D5A4DB00A124A29DF27FADB5C4216AC3D47438B9878CE=MKL
	
	public String updateSenhaUser() {
		try {

			this.getUsuario().setSenha(senha);
			getUserFacade().updateUser(this.usuario);
			return "/pages/public/novaSenhaRetorno.jsf?faces-redirect=true";
			
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}
		return userFacade;
	}

	public String getParametroUser() {
		return parametroUser;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



}
