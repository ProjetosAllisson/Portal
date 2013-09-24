package br.com.allisson.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import br.com.allisson.facade.DuplicataFacade;
import br.com.allisson.modelo.Duplicata;
import br.com.allisson.modelo.User;

@ManagedBean(name = "duplicatasBean")
public class DuplicatasBean {

	@ManagedProperty(value = BoletosBean.INJECTION_BOLETO)
	private BoletosBean boletosBean;

	private DuplicataFacade duplicataFacade;
	private List<Duplicata> duplicatas;
	private Duplicata duplicataSelecionada;

	private User usuario = new User();

	private DuplicataFacade getDuplicataFacade() {
		if (duplicataFacade == null) {
			duplicataFacade = new DuplicataFacade();
		}
		return duplicataFacade;
	}

	private void loadDuplicatasEmAberto() {

		this.usuario = usuario.DevolveUsuarioSessao();

		if (usuario.consultaPorGrupoCliente()) {
			duplicatas = getDuplicataFacade()
					.duplicatasEmAbertoPorGrupoCliente();
		} else {
			duplicatas = getDuplicataFacade().duplicatasEmAberto();
		}
	}

	public List<Duplicata> getduplicatasEmAberto() {
		this.loadDuplicatasEmAberto();
		return duplicatas;
	}

	public BoletosBean getBoletosBean() {
		return boletosBean;
	}

	public void setBoletosBean(BoletosBean boletosBean) {
		this.boletosBean = boletosBean;
	}

	public Duplicata getDuplicataSelecionada() {
		return duplicataSelecionada;
	}

	public void setDuplicataSelecionada(Duplicata duplicataSelecionada) {
		this.duplicataSelecionada = duplicataSelecionada;
		this.boletosBean.setDuplicata(this.duplicataSelecionada);

	}

}
