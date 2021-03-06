package br.com.allisson.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.event.RowEditEvent;

import br.com.allisson.bean.converter.ClienteConverter;
import br.com.allisson.bean.converter.DestinatarioConverter;
import br.com.allisson.facade.ClienteFacade;
import br.com.allisson.facade.ColetaFacade;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.ClienteGrupo;
import br.com.allisson.modelo.User;
import br.com.allisson.modelo.coleta.Coleta;
import br.com.allisson.modelo.coleta.ColetaAutorizadaEnum;
import br.com.allisson.modelo.coleta.ColetaItem;
import br.com.allisson.modelo.coleta.ColetaStatusEnum;
import br.com.allisson.modelo.coleta.TipoFrete;

@ManagedBean(name = "coletaBean")
@ViewScoped
public class ColetaBean extends AbstractMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Coleta coleta = new Coleta();

	private List<Coleta> coletas;

	private ColetaFacade coletaFacade = new ColetaFacade();

	private Coleta coletaSelecionada = new Coleta();

	private ClienteFacade clienteFacade = new ClienteFacade();

	// ------Codificacao antiga------------------//--------------

	private ColetaItem item;
	private List<ColetaItem> coletaItens;

	private List<ClienteGrupo> grpsRemetente;
	private List<Cliente> clientesRemetente;
	
	private Cliente destinatario;

	// private ClienteGrupoFacade grupoFacade = new ClienteGrupoFacade();

	private User usuario = new User();
	private ClienteGrupo grupoRemet = new ClienteGrupo();

	// private Veiculo veiculo = new Veiculo();

	@PostConstruct
	public void init() {
		item = new ColetaItem();
		coletaItens = new ArrayList<ColetaItem>();

		// VeiculoDAO vei = new VeiculoDAO();

		// vei.beginTransaction();

		// for (Veiculo v : vei.findAllVeiculo()) {
		// System.out.println(v.getId().getPlaca());
		// veiculo = v;
		// break;
		// }

		this.setUsuario(getUsuario().DevolveUsuarioSessao());

		// if (usuario.getCliente().getGrupoCliente()!=null){
		// setGrupoRemet(grupoFacade.findGrupo(usuario.getCliente().getGrupoCliente().getGrupo()));
		// }
		
		this.completeCliente("");
		this.completeDestinatario("");

	}

	

	public List<Cliente> completeCliente(String query) {

		List<Cliente> clientes = new ArrayList<Cliente>();

		clientes.addAll(clienteFacade.allClientesPorNome(query));

		ClienteConverter.setDB(clientes);

		return clientes;
	}

	public List<Cliente> completeDestinatario(String query) {
		List<Cliente> clientes = new ArrayList<Cliente>();

		clientes.addAll(clienteFacade.allClientesPorNome(query));

		DestinatarioConverter.setDB(clientes);

		return clientes;

	}

	public void incluir() {
		coletaSelecionada = new Coleta();
		coletaSelecionada.setRemetente(usuario.getCliente());
		coletaSelecionada.setTipoFrete(TipoFrete.FOB);
		item = new ColetaItem();
		coletaItens = new ArrayList<ColetaItem>();
	}

	public void alterar() {
		// completeCliente(coletaSelecionada.getRemetente().getNome());
		// completeDestinatario(coletaSelecionada.getDestinatario().getNome());
		item = new ColetaItem();
		coletaItens = new ArrayList<ColetaItem>();
		setColetaItens(coletaSelecionada.getItensColeta());
		
		destinatario = new Cliente();
		destinatario = coletaSelecionada.getDestinatario();
	}

	public void incluirColeta() {

		coletaSelecionada.setItensColeta(coletaItens);
		coletaSelecionada.setUser(getUsuario());

		Calendar c =  Calendar.getInstance();
		coletaSelecionada.setEmissao(c);
		
		Calendar dia = Calendar.getInstance();
		dia = DateUtils.truncate(dia, Calendar.DAY_OF_MONTH);
		coletaSelecionada.setDiapedido(dia);

		coletaSelecionada.setAutorizada(ColetaAutorizadaEnum.NAO);
		coletaSelecionada.setVlrCobrado(new BigDecimal(0));

		for (ColetaItem col : coletaSelecionada.getItensColeta()) {
			col.setColeta(coletaSelecionada);
		}
		coletaFacade.createColeta(coletaSelecionada);

		closeDialog();
		displayInfoMessageToUser("Coleta incluida com Sucesso");
		loadColetas();

	}

	public void valorizar(RowEditEvent event) {

		System.out.println(((Coleta) event.getObject()).getVlrCobrado());

		System.out.println(coletaSelecionada.getVlrCobrado());
		if (coletaSelecionada.getVlrCobrado().compareTo(new BigDecimal(0)) > 0) {
			coletaFacade.updateColeta(coletaSelecionada);
		}

	}

	public void alterarColeta() {
		coletaSelecionada.setItensColeta(coletaItens);

		for (ColetaItem col : coletaSelecionada.getItensColeta()) {
			col.setColeta(coletaSelecionada);
		}
		if (coletaSelecionada.getDestinatario()==null){
			coletaSelecionada.setDestinatario(destinatario);	
		}
		
		coletaFacade.updateColeta(coletaSelecionada);

		closeDialog();
		displayInfoMessageToUser("Coleta alterada com Sucesso");

		loadColetas();
	}

	public void deleteColeta() {
		try {
			coletaFacade.deleteColeta(coletaSelecionada);

			closeDialog();
			displayInfoMessageToUser("Exclu�do com sucesso");
			loadColetas();

		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public void cancelarColeta() {
		try {
			coletaSelecionada.setSituacaoColeta(ColetaStatusEnum.CANCELADO);
			Calendar c = Calendar.getInstance();
			coletaSelecionada.setCancelamento(c);
			coletaFacade.updateColeta(coletaSelecionada);

			closeDialog();
			displayInfoMessageToUser("Cancelamento Solicitado com sucesso");
			loadColetas();

		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();

		}
	}

	public String reinit() {

		item = new ColetaItem();

		return null;

	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public List<ColetaItem> getColetaItens() {
		return coletaItens;
	}

	public void setColetaItens(List<ColetaItem> coletaItens) {
		this.coletaItens = coletaItens;
	}

	public ColetaItem getItem() {
		return item;
	}

	public void setItem(ColetaItem item) {
		this.item = item;
	}

	public List<ClienteGrupo> getGrpsRemetente() {
		return grpsRemetente;
	}

	public void setGrpsRemetente(List<ClienteGrupo> grpsRemetente) {
		this.grpsRemetente = grpsRemetente;
	}

	public List<Cliente> getClientesRemetente() {
		return clientesRemetente;
	}

	public void setClientesRemetente(List<Cliente> clientesRemetente) {
		this.clientesRemetente = clientesRemetente;
	}

	public ClienteGrupo getGrupoRemet() {
		return grupoRemet;
	}

	public void setGrupoRemet(ClienteGrupo grupoRemet) {
		this.grupoRemet = grupoRemet;
	}

	public List<Coleta> getColetas() {

		try {
			if (coletas == null)
				loadColetas();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return coletas;
	}

	private void loadColetas() {
		try {
			setColetas(coletaFacade.getColetas());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setColetas(List<Coleta> coletas) {
		this.coletas = coletas;
	}

	public Coleta getColetaSelecionada() {
		return coletaSelecionada;
	}

	public void setColetaSelecionada(Coleta coletaSelecionada) {

		if ((coletaSelecionada != null)
				&& (!coletaSelecionada.getRemetente().getNome().equals(""))) {
			this.completeCliente(coletaSelecionada.getRemetente().getNome());
		}

		if ((coletaSelecionada != null)
				&& (coletaSelecionada.getDestinatario() != null)
				&& (!coletaSelecionada.getDestinatario().getNome().equals(""))) {
			this.completeDestinatario(coletaSelecionada.getDestinatario()
					.getNome());
		}

		this.coletaSelecionada = coletaSelecionada;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public void fatorKg() {
		BigDecimal multiplica = new BigDecimal(item.getVolumes());
		BigDecimal kgs = new BigDecimal(0);

		kgs = multiplica.multiply(new BigDecimal(30));
		this.item.setKgsCubado(kgs);
	}
	
	

	
}
