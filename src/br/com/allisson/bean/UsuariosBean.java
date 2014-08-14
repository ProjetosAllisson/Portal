package br.com.allisson.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.allisson.dao.EmpresaDAO;
import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.Empresa;
import br.com.allisson.modelo.Mensagem;
import br.com.allisson.modelo.Role;
import br.com.allisson.modelo.User;
import br.com.allisson.util.EmailUtils;

@ManagedBean(name = "usuariosBean")
@SessionScoped
public class UsuariosBean extends AbstractMB {

	public static final String INJECTION_NAME = "#{usuariosBean}";

	private User usuario;
	private UserFacade userFacade;

	private User usuarioSelecionado;
	private User[] usuariosSelecionados;
	private List<User> users;
	private List<User> usersNaoAutorizados;

	private Cliente cliente;
	private String cnpj;
	
	private boolean campoBooleanControle;

	public UsuariosBean() {
		resetUser();
	}

	public void InserirUsuario() {
		usuario.setCliente(cliente);

		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			displayErrorMessageToUser("Registro Duplicado. O Usuário que você esta inserindo ja existe");
		} else {
			usuario.setRole(Role.USER);

			getUserFacade().createUser(usuario);

			resetUser();
			loadUsers();

			displayInfoMessageToUser("Usuário inserido com sucesso");

		}
		
	}
	
	
	public void InserirUsuarioExterno(){

		usuario.setCliente(cliente);
		
		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			displayErrorMessageToUser("Registro Duplicado. O Usuário que você esta inserindo ja existe");
		} else {
			usuario.setRole(Role.USER);
			usuario.setAcesso_autorizado(false);
			usuario.setGrupoClientes(false);

			getUserFacade().createUser(usuario);

			resetUser();
			loadUsers();

			displayInfoMessageToUser("Usuário inserido com sucesso");
			
			
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("usuario_sucesso.jsf");

			} catch (IOException e) {
				e.printStackTrace();
			}


		}
		
		

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
		if (usuarioSelecionado != null) {
			this.usuarioSelecionado.setCliente(cliente);
		}
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}
		return userFacade;
	}

	public List<User> getAllUsers() {
		// if (users == null) {
		loadUsers();
		// }
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
		usuarioSelecionado = new User();
		cliente = new Cliente();
	}

	public void deleteUser() {
		try {
			System.out.println("Usuario" + usuarioSelecionado.getLogin());
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

	public void updateUser() {
		try {
			getUserFacade().updateUser(usuarioSelecionado);
			
			if (usuarioSelecionado.getAcesso_autorizado()){
				this.enviarEmailAcessoAutorizado(usuarioSelecionado);
			}
			
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

	public User[] getUsuariosSelecionados() {
		return usuariosSelecionados;
	}

	public void setUsuariosSelecionados(User[] usuariosSelecionados) {
		this.usuariosSelecionados = usuariosSelecionados;
	}

	public void autorizaUsuarios() {
		for (User usuario : getUsuariosSelecionados()) {
			usuario.setAcesso_autorizado(true);
			getUserFacade().updateUser(usuario);
			
			
			this.enviarEmailAcessoAutorizado(usuario);
		}
		loadUsersNaoAutorizados();
	}

	
	private void enviarEmailAcessoAutorizado(User usuario) {
		
		EmpresaDAO empresaDao = new EmpresaDAO();
		Empresa empresa = new Empresa();
		
		empresa = empresaDao.DadosTransportadora();
		
		if (!usuario.getEmail().equals("")){
			Mensagem msgAdmin = new Mensagem();
			
			msgAdmin.setTitulo("Acesso ao Portal Web");
			msgAdmin.setDestino(usuario.getEmail());
			
			List<String> corpoEmail = new ArrayList<String>();
			
			corpoEmail.clear();
			corpoEmail.add("Ola,");
			corpoEmail.add("");
						
			
			corpoEmail.add("Informamos que seu usuário: "+usuario.getLogin()+ " foi autorizado a acessar o Portal Web");
			corpoEmail.add("da transportadora "+empresa.getNome()+".");
			
			corpoEmail.add("");
			
			corpoEmail.add("Agradecemos a preferência.");
			
			msgAdmin.setMensagens(corpoEmail);
			
			EmailUtils.enviarEmail(msgAdmin);
			
		}
		
	}

	public String getCnpj() {
		
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		cliente.setCgc(cnpj);
		this.cnpj = cnpj;
	}

	public boolean isCampoBooleanControle() {
		return campoBooleanControle;
	}

	public void setCampoBooleanControle(boolean campoBooleanControle) {
		this.campoBooleanControle = campoBooleanControle;
	}

}
