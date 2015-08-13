package br.com.allisson.bean;

import java.math.BigDecimal;
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

	private BigDecimal totalMercadoria;
	private BigDecimal totalPeso;
	private BigDecimal totalPesoCub;
	private BigDecimal totalFretePeso;
	private BigDecimal totalAdValorem;
	private BigDecimal totalSeguro;
	private BigDecimal totalPedagio;
	private BigDecimal totalDespacho;
	private BigDecimal totalTDE;
	private BigDecimal totalOutros;
	private BigDecimal totalFrete;

	public void pesquisar() {
		lista = extrato.extratoDuplicatasEmitidas(filtro);
		//this.totais();
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

	private void totais() {

		totalMercadoria = BigDecimal.ZERO;
		totalPeso = BigDecimal.ZERO;
		totalPesoCub = BigDecimal.ZERO;
		totalFretePeso = BigDecimal.ZERO;
		totalAdValorem = BigDecimal.ZERO;
		totalSeguro = BigDecimal.ZERO;
		totalPedagio = BigDecimal.ZERO;
		totalDespacho = BigDecimal.ZERO;
		totalTDE = BigDecimal.ZERO;
		totalOutros = BigDecimal.ZERO;
		totalFrete = BigDecimal.ZERO;

		for (ExtratoDuplicata ex : lista) {
			totalMercadoria = totalMercadoria.add(ex.getVlr_merc());
			totalPeso = totalPeso.add(ex.getPeso());
			totalPesoCub = totalPesoCub.add(ex.getPeso_cub());
			totalFretePeso = totalFretePeso.add(ex.getFrt_peso());
			totalAdValorem = totalAdValorem.add(ex.getAdvalorem());
			totalSeguro = totalSeguro.add(ex.getVlr_seguro());
			totalPedagio = totalPedagio.add(ex.getPedagio());
			totalDespacho = totalDespacho.add(ex.getDespacho());
			totalTDE = totalTDE.add(ex.getTx_dif_acesso());
			totalOutros = totalOutros.add(ex.getOutros());
			totalFrete = totalFrete.add(ex.getTot_frete());
		}

	}

	public BigDecimal getTotalMercadoria() {
		return totalMercadoria;
	}

	public BigDecimal getTotalPeso() {
		return totalPeso;
	}

	public BigDecimal getTotalPesoCub() {
		return totalPesoCub;
	}

	public BigDecimal getTotalFretePeso() {
		return totalFretePeso;
	}

	public BigDecimal getTotalAdValorem() {
		return totalAdValorem;
	}

	public BigDecimal getTotalSeguro() {
		return totalSeguro;
	}

	public BigDecimal getTotalPedagio() {
		return totalPedagio;
	}

	public BigDecimal getTotalDespacho() {
		return totalDespacho;
	}

	public BigDecimal getTotalTDE() {
		return totalTDE;
	}

	public BigDecimal getTotalOutros() {
		return totalOutros;
	}

	public BigDecimal getTotalFrete() {
		return totalFrete;
	}

}
