package br.com.allisson.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.brazilutils.br.cpfcnpj.CpfCnpj;

import br.com.allisson.facade.ClienteFacade;
import br.com.allisson.modelo.Cliente;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean {

	@ManagedProperty(value = UsuariosBean.INJECTION_NAME)
	private UsuariosBean usuariosBean;

	private Cliente cliente;
	private List<Cliente> clientes;
	private ClienteFacade clienteFacade;
	private Cliente clienteSelecionado;

	private String nomeCliente;

	private boolean disabled = true;

	private void resetClientes() {
		clientes = new ArrayList<Cliente>();
		this.nomeCliente = "";
		// clienteSelecionado = new Cliente();
	}

	public void localizaCliente(String cliente) {
		if (!cliente.equals("")) {

			CpfCnpj cpf = new CpfCnpj();

			cpf.setCpfCnpj(cliente);

			if (cpf.isCpf()) {
				System.out.println(cpf.toString());
				setCliente(getClienteFacade().localiza(cpf.toString()));

			} else {
				if (cpf.isCnpj()) {

					setCliente(getClienteFacade().localiza(cpf.toString()));
				}
			}

			// setCliente(getClienteFacade().localiza(cliente));
		}
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
		if (clienteFacade == null) {
			clienteFacade = new ClienteFacade();
		}
		return clienteFacade;
	}

	public List<Cliente> allClientes() {
		if (getClientes() == null) {
			setClientes(getClienteFacade().allClientes());
		}

		return getClientes();

	}

	public List<Cliente> localizaClientes() {
		if (!getNomeCliente().equals("") && (getNomeCliente().length() > 4)) {
			setClientes(getClienteFacade().allClientesPorNome(getNomeCliente()));
		}

		return getClientes();
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente.toUpperCase();
	}

	public UsuariosBean getUsuariosBean() {
		return usuariosBean;
	}

	public void setUsuariosBean(UsuariosBean usuariosBean) {
		this.usuariosBean = usuariosBean;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public void clienteEscolhido() {
		this.setDisabled(true);
		if (this.clienteSelecionado != null) {
			this.setDisabled(false);
			this.usuariosBean.setCliente(this.clienteSelecionado);

			resetClientes();
		}
	}

}
