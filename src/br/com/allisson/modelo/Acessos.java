package br.com.allisson.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_ACESSOS")

public class Acessos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String login;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dt_acesso;
	
	@ManyToOne
	@JoinColumn(name ="cod_usuario")
	private User user;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Calendar getDt_acesso() {
		return dt_acesso;
	}
	public void setDt_acesso(Calendar dt_acesso) {
		this.dt_acesso = dt_acesso;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDt_AcessoFormatada(){
		// popula a data de finalizacao da tarefa, fazendo a conversao
		/*
		* Date data = rs.getDate("dataFinalizacao"); if(data != null) {
		* Calendar dataFinalizacao = Calendar.getInstance();
		* dataFinalizacao.setTime(data);
		* tarefa.setDataFinalizacao(dataFinalizacao); }
		*/
		
		return this.dt_acesso.getTime(); 
		
	}
	
}
