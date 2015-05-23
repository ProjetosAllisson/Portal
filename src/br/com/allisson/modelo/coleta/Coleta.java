	package br.com.allisson.modelo.coleta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.User;

@Entity
@Table(name="TBL_COLETA")
@NamedQueries({
	@NamedQuery(name="Coleta.findAll", query="select distinct c from Coleta c left join fetch c.itensColeta order by c.emissao desc"),
	@NamedQuery(name="Coleta.findAllUser",query="select distinct c from Coleta c left join fetch c.itensColeta where c.user =:user order by c.emissao desc")
	
})
public class Coleta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Coleta.findAll";
	public static final String FIND_ALL_USER = "Coleta.findAllUser";
	
	@Id
	@SequenceGenerator(name="Coleta", sequenceName="Col_Seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="Coleta")
	private int id;
	
	@OneToMany(mappedBy="coleta",targetEntity = ColetaItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	private List<ColetaItem> itensColeta = new ArrayList<ColetaItem>();
	
	@OneToMany(mappedBy="coleta",targetEntity = ColetaEvento.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<ColetaEvento> eventos = new ArrayList<ColetaEvento>();
	
	@ManyToOne
	@JoinColumn(name="cod_usuario")
	private User user;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar emissao;
	
	@ManyToOne
	@JoinColumn(name="remetente",columnDefinition="VARCHAR(18)")
	private Cliente remetente;
	
	@ManyToOne
	@JoinColumn(name="destinatario",columnDefinition="VARCHAR(18)")
	private Cliente destinatario;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TipoFrete tipoFrete;
	
	@Column
	private BigDecimal vlrCobrado;
	
	
	@Column
	@Enumerated(EnumType.STRING)
	private ColetaAutorizadaEnum autorizada; 
	
	
	@Column(insertable=false,updatable=false, columnDefinition="VARCHAR(3)")
	private String fil_coleta;
	
	@Column(insertable=false,updatable=false)
	private Integer nr_coleta;
	
	@Column(insertable=false,updatable=false, columnDefinition="VARCHAR(17)")
	private String status;
	
	
	//@OneToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "veiculo",updatable=true)
	//private Veiculo veiculo = new Veiculo();
 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*
	@Override
	public String toString() {
		return "Coleta [";
	}
	*/
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Coleta) {
			Coleta coleta = (Coleta) obj;
			return coleta.getId() == id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getId();
	}

	public List<ColetaItem> getItensColeta() {
		return itensColeta;
	}

	public void setItensColeta(List<ColetaItem> itensColeta) {
		this.itensColeta = itensColeta;
	}

	public Cliente getRemetente() {
		return remetente;
	}

	public void setRemetente(Cliente remetente) {
		this.remetente = remetente;
	}

	public Cliente getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Cliente destinatario) {
		this.destinatario = destinatario;
	}

	public TipoFrete getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(TipoFrete tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getEmissao() {
		return emissao;
	}

	public void setEmissao(Calendar emissao) {
		this.emissao = emissao;
	}


	public BigDecimal getVlrCobrado() {
		return vlrCobrado;
	}

	public void setVlrCobrado(BigDecimal vlrCobrado) {
		this.vlrCobrado = vlrCobrado;
	}

	public ColetaAutorizadaEnum getAutorizada() {
		return autorizada;
	}

	public void setAutorizada(ColetaAutorizadaEnum autorizada) {
		this.autorizada = autorizada;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNr_coleta() {
		return nr_coleta;
	}

	public void setNr_coleta(Integer nr_coleta) {
		this.nr_coleta = nr_coleta;
	}

	public String getFil_coleta() {
		return fil_coleta;
	}

	public void setFil_coleta(String fil_coleta) {
		this.fil_coleta = fil_coleta;
	}

}
