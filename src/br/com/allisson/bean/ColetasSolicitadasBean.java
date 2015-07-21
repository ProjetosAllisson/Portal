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
import org.primefaces.model.chart.PieChartModel;

import br.com.allisson.facade.ColetaFacade;
import br.com.allisson.modelo.User;

@ManagedBean(name="coletasSolicitadas")
@RequestScoped
public class ColetasSolicitadasBean {
	
	private CartesianChartModel model;
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	private PieChartModel pieModel;
	
	
	private ColetaFacade coletaFacade = new ColetaFacade();
	
	private User usuario = new User();
	
	/*
	@PostConstruct
	public void init(){
		this.model = new CartesianChartModel();
		
		adicionarSerie("Todas as Coletas",null);
		adicionarSerie("Minhas Coletas",getUsuario().DevolveUsuarioSessao());
		
	}
	*/
	public void preRender(){
		this.model = new CartesianChartModel();
		
		adicionarSerie("Todas as Coletas",null);
		adicionarSerie("Minhas Coletas",getUsuario().DevolveUsuarioSessao());
		
		criarPieModel();
		
		
	}

	private void criarPieModel() {
		pieModel = new PieChartModel();
		
		Map<String, BigDecimal> valoresPorUsuario = coletaFacade.valoresTotaisPorUsuario(15, getUsuario().DevolveUsuarioSessao());
		
		for (String string : valoresPorUsuario.keySet()) {
			pieModel.set(string, valoresPorUsuario.get(string));
			
		}
		
	}

	private void adicionarSerie(String rotulo, User criadoPor) {
		ChartSeries series = new ChartSeries(rotulo);
		
		Map<Date, BigDecimal> dados = coletaFacade.valoresTotaisPorDate(15, criadoPor);
		
		
		
		
		for (Date data :dados.keySet()){
			
			series.set(DATE_FORMAT.format(data), dados.get(data));
			
			System.out.println(data);
			System.out.println(DATE_FORMAT.format(data));
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

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	

	
}
