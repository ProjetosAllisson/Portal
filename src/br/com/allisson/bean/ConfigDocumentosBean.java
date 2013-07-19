package br.com.allisson.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="configDocumentosBean")
@RequestScoped
public class ConfigDocumentosBean {
	
	public boolean getColunaEmbarque(){
		return true;
	}
	
	public boolean getColunaChegada(){
		return true;
	}

}
