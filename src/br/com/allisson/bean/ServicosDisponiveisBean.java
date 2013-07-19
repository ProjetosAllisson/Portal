package br.com.allisson.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.allisson.factory.ServicosDisponiveisFactory;
import br.com.allisson.modelo.ServicosDisponiveis;
import br.com.allisson.util.Criptografia;

@ManagedBean(name ="servDisponiveis")
@RequestScoped
public class ServicosDisponiveisBean {
	
	private ServicosDisponiveis servico;

	public ServicosDisponiveisBean(){
		this.setServico(new ServicosDisponiveisFactory().getServicos());
	}

	public ServicosDisponiveis getServico() {
		return servico;
	}
	
	public boolean rastreamento(){
						
		return (this.getServico().getRastreamento().equals(Criptografia.md5("SIM")));
	}

	public void setServico(ServicosDisponiveis servico) {
		this.servico = servico;
	}
}
