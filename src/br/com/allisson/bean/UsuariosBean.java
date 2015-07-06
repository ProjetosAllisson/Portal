package br.com.allisson.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.allisson.bean.converter.ClienteConverter;
import br.com.allisson.facade.ClienteFacade;
import br.com.allisson.facade.UserFacade;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.Role;
import br.com.allisson.modelo.User;

@ManagedBean(name = "usuariosBean")
@ViewScoped
public class UsuariosBean extends AbstractMB {

	// public static final String INJECTION_NAME = "#{usuariosBean}";

	private User usuario;
	private UserFacade userFacade;

	private User usuarioSelecionado;
	private User[] usuariosSelecionados;
	private List<User> users;
	private List<User> usersNaoAutorizados;

	private Cliente cliente;
	private String cnpj;

	private Cliente clienteSelecionado = new Cliente();
	private ClienteFacade clienteFacade;

	private boolean campoBooleanControle=true;

	
	public UsuariosBean() {
		this.cnpj ="";
		resetUser();
		this.completeCliente("");
	}

	public String InserirUsuario() {

		// System.out.println(clienteSelecionado.getNome());

		usuario.setCliente(clienteSelecionado);

		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			displayErrorMessageToUser("Registro Duplicado. O Usuário que você esta inserindo ja existe");
		} else {

			usuario.setRole(usuarioSelecionado.getRole());

			getUserFacade().createUser(usuario);

			resetUser();
			loadUsers();

			displayInfoMessageToUser("Usuário inserido com sucesso");

		}
		return "";
	}

	public String InserirUsuarioExterno() {

		usuario.setCliente(cliente);

		if (getUserFacade().isExists(usuario.getLogin()) == true) {
			keepDialogOpen();
			displayErrorMessageToUser("Registro Duplicado. O Usuário que você esta inserindo ja existe");
		} else {
			usuario.setRole(Role.USER);
			usuario.setAcesso_autorizado(false);
			usuario.setGrupoClientes(false);

			getUserFacade().createUser(usuario);

			closeDialog();
			displayInfoMessageToUser("Usuário inserido com sucesso. Aguarde Liberação");
			
			resetUser();
			//loadUsers();


			
/*
 * 
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("usuario_sucesso.jsf");

			} catch (IOException e) {
				e.printStackTrace();
			}*/

		}
		
		return "";

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
		if (getUsers() == null) {
			loadUsers();
		 }
		return getUsers();
	}

	public List<User> getAllUsersNaoAutorizados() {
		loadUsersNaoAutorizados();

		return usersNaoAutorizados;
	}

	private void loadUsersNaoAutorizados() {
		usersNaoAutorizados = getUserFacade().listAllNaoAutorizados();
	}

	private void loadUsers() {
		setUsers(getUserFacade().listAll());

	}

	public void usuarioSelecionado(User usuarioSelecionado) {
		this.setUsuarioSelecionado(usuarioSelecionado);
	}

	public void resetUser() {
		usuario = new User();
		usuarioSelecionado = new User();
		cliente = new Cliente();
		// clienteSelecionado = new Cliente();
	}

	public void deleteUser() {
		try {
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

			if (usuarioSelecionado.getAcesso_autorizado()) {
				try {
				    getUserFacade().enviarEmailAcessoAutorizado(usuarioSelecionado);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

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
		if ((usuarioSelecionado != null)
				&& (!usuarioSelecionado.getCliente().getNome().equals(""))) {
			this.completeCliente(usuarioSelecionado.getCliente().getNome());
		}
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

			getUserFacade().autorizaUsuario(usuario);

			/*
			 * usuario.setAcesso_autorizado(true);
			 * getUserFacade().updateUser(usuario);
			 * 
			 * 
			 * getUserFacade().enviarEmailAcessoAutorizado(usuario);
			 */
		}
		displayInfoMessageToUser("Login(s) Autorizado(s) com Sucesso");
		loadUsersNaoAutorizados();
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

	

	
	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public List<Cliente> completeCliente(String query) {

		List<Cliente> clientes = new ArrayList<Cliente>();

		// if(!query.equals("")){
		clientes.addAll(getClienteFacade().allClientesPorNome(query));
		// }

		ClienteConverter.setDB(clientes);

		return clientes;
	}

	public ClienteFacade getClienteFacade() {
		if (clienteFacade == null) {
			clienteFacade = new ClienteFacade();
		}
		return clienteFacade;
	}

	public void setClienteFacade(ClienteFacade clienteFacade) {
		this.clienteFacade = clienteFacade;
	}

	public Role[] getPerfis() {
		return Role.values();
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
