package br.com.allisson.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.allisson.facade.ClienteFacade;
import br.com.allisson.modelo.Cliente;

@ManagedBean(name ="clienteBean")
@SessionScoped
public class ClienteBean {
	
	private Cliente cliente;
	private ClienteFacade clienteFacade;
	
	private boolean disabled = true; 
	
	public void localizaCliente(String cliente){
		setCliente(getClienteFacade().localiza(cliente));

		this.setDisabled(getCliente() == null);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public ClienteFacade getClienteFacade() {
		if (clienteFacade == null){
			clienteFacade = new ClienteFacade();
		}		
		return clienteFacade;
	}

	public List<Cliente> allClientes(){
		return getClienteFacade().allClientes();
	}
	
	
}
