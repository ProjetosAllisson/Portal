package br.com.allisson.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.allisson.facade.AcessosFacade;
import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractMB {

	@ManagedProperty(value = UserBean.INJECTION_NAME)
	private UserBean userBean;

	private User usuarioAutenticado;

	private String usuario;

	private String senha;

	private HttpSession session;

	
	private boolean logado = false;

	@PostConstruct
	public void init() {
		// Chamado só quando o managed bean é colocado no escopo view,
		// e não a cada requisição como acontecia com o escopo request
		// usuario = new Usuario();
	}

	public void login() {
		// return "login";
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("loginBean", new LoginBean());
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("login.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String autentica() {

		this.autenticacao();

		if (logado) {
			
			return "/pages/protected/index.jsf?faces-redirect=true";
			/*
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../protected/index.jsf");

			} catch (IOException e) {
				e.printStackTrace();
			}

			*/
		}
		return null;

	}

	public String autenticaLoginFrame() {

		this.autenticacao();
		
		if (logado) 
			return "/pages/protected/index.jsf?faces-redirect=true";

		else
			return "loginSemAcesso.jsf?faces-redirect=true";
		
	}

	private void autenticacao() {

		UserFacade userFacade = new UserFacade();

		RequestContext context = RequestContext.getCurrentInstance();
		this.senha = Criptografia.md5(this.senha);

		usuarioAutenticado = userFacade.isValidLogin(usuario, senha);

		if ((usuarioAutenticado != null)
				&& (usuarioAutenticado.getAcesso_autorizado() == false)) {
			displayErrorMessageToUser("Usuário ainda não foi autorizado o acesso!");
		}

		else if (usuarioAutenticado != null) {
			logado = true;

			displayInfoMessageToUser("Acesso ao Portal Web, Seja bem-vindo "
					+ usuario);

			userBean.setUser(usuarioAutenticado);

			FacesContext ctx = FacesContext.getCurrentInstance();
			session = (HttpSession) ctx.getExternalContext().getSession(false);
			session.setAttribute("usuarioAutenticado", usuarioAutenticado);

			AcessosFacade acessosFacade = new AcessosFacade();
			acessosFacade.createAcesso(usuarioAutenticado);

		} else {
			displayErrorMessageToUser("Erro ao efetuar login, Usuário ou Senha incorretos");

		}

		context.addCallbackParam("logado", logado);

	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	
	public String logOut() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);

		session.removeAttribute("usuarioAutenticado");
		session.invalidate();
		
		getRequest().getSession().invalidate();
		
		userBean = null;
		
		
		logado=false;
		
		return "/pages/public/login.jsf?faces-redirect=true";
		
	}

		
	
	
	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
