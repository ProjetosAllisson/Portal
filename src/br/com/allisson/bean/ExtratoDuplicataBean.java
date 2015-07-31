package br.com.allisson.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.allisson.dao.ExtratoDuplicataDAO;
import br.com.allisson.modelo.ExtratoDuplicata;


@ManagedBean(name = "duplViewBean")
@ViewScoped
public class ExtratoDuplicataBean extends AbstractMB {

	private ExtratoDuplicataDAO extrato = new ExtratoDuplicataDAO();
	
	private List<ExtratoDuplicata> lista = new ArrayList<ExtratoDuplicata>();

	public List<ExtratoDuplicata> getLista() {
		
		extrato.beginTransaction();
		
		lista.addAll(extrato.findAll());
		
		extrato.closeTransaction();
		
		return lista;
	}

	public void setLista(List<ExtratoDuplicata> lista) {
		this.lista = lista;
	}
	
}
