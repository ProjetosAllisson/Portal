package br.com.allisson.modelo.coleta;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TBL_COLETA_EVENTOS")
public class ColetaEvento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ColetaEventos", sequenceName="ColEventos_Seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ColetaEventos")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="coleta_id")
	private Coleta coleta;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data_hora;

	@Column
	@Enumerated(EnumType.STRING)
	private ColetaEventosEnum evento;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getData_hora() {
		return data_hora;
	}

	public void setData_hora(Calendar data_hora) {
		this.data_hora = data_hora;
	}

	public ColetaEventosEnum getEvento() {
		return evento;
	}

	public void setEvento(ColetaEventosEnum evento) {
		this.evento = evento;
	}

}
