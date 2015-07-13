package br.com.allisson.bean;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.allisson.facade.ColetaFacade;

@ManagedBean(name="coletasSolicitadas")
@RequestScoped
public class ColetasSolicitadasBean {
	
	private CartesianChartModel model;
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	private ColetaFacade coletaFacade = new ColetaFacade();
	
	public void preRender(){
		this.model = new CartesianChartModel();
		
		adicionarSerie("Todas as Coletas");
		adicionarSerie("Meus Pedidos");
	}

	private void adicionarSerie(String rotulo) {
		ChartSeries series = new ChartSeries(rotulo);
		
		Map<Date, BigDecimal> dados = coletaFacade.valoresTotaisPorDate(15, null);
		
		
		for (Date data :dados.keySet()){
			series.set(DATE_FORMAT.format(data), dados.get(data));
		}
		//series.set("1", Math.random() * 1000);
		//series.set("2", Math.random() * 1000);
		//series.set("3", Math.random() * 1000);
		//series.set("4", Math.random() * 1000);
		//series.set("5", Math.random() * 1000);
		
		this.model.addSeries(series);
		
		
	}

	public CartesianChartModel getModel() {
		return model;
	}

	
}
