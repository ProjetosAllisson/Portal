package br.com.allisson.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.allisson.facade.ExtratoDuplicataFacade;
import br.com.allisson.modelo.ExtratoDuplicata;
import br.com.allisson.modelo.FiltroExtratoDuplicata;


@ManagedBean(name = "duplViewBean")
@ViewScoped
public class ExtratoDuplicataBean extends AbstractMB {

	private ExtratoDuplicataFacade extrato = new ExtratoDuplicataFacade();
	
	private List<ExtratoDuplicata> lista = new ArrayList<ExtratoDuplicata>();
	
	private FiltroExtratoDuplicata filtro = new FiltroExtratoDuplicata();
	
	private ExtratoDuplicata duplicataSelecionada; 

	
	public void pesquisar(){
		lista = extrato.extratoDuplicatasEmitidas(filtro);
		System.out.println(lista.size());
	}
	
	public List<ExtratoDuplicata> getLista() {
		return lista;
	}

	
	public FiltroExtratoDuplicata getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroExtratoDuplicata filtro) {
		this.filtro = filtro;
	}

	public ExtratoDuplicata getDuplicataSelecionada() {
		return duplicataSelecionada;
	}

	public void setDuplicataSelecionada(ExtratoDuplicata duplicataSelecionada) {
		this.duplicataSelecionada = duplicataSelecionada;
	}
	
}
