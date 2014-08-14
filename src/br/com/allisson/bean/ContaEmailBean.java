package br.com.allisson.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.allisson.facade.ContaEmailFacade;
import br.com.allisson.modelo.ContaEmail;

@ManagedBean(name="contaEmailBean")
@RequestScoped
public class ContaEmailBean extends AbstractMB{
	
	private ContaEmail dadosConta = new ContaEmail();
	private ContaEmail contaExistente = new ContaEmail();
	
	
	private ContaEmailFacade contaFacade = new ContaEmailFacade();
	
	
	@PostConstruct
	public void inicializa(){
		if (ContaEmailFacade.getDadosContaEmail()!=null){
			dadosConta = ContaEmailFacade.getDadosContaEmail();
		}
		contaExistente = dadosConta;
	}
	
	public void GravarContaEmail(){
		
		
		if (contaExistente == null){
			contaFacade.createConta(this.dadosConta);	
		}else {
			contaFacade.updateConta(this.dadosConta);
		}
		
		displayInfoMessageToUser("Conta gravada com sucesso");
	}

	public ContaEmail getDadosConta() {
		return dadosConta;
	}

	public void setDadosConta(ContaEmail dadosConta) {
		this.dadosConta = dadosConta;
	}


}
