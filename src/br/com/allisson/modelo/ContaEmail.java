package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_CONTAEMAIL")
//@SequenceGenerator(name="conta",sequenceName="SEQUENCE_CONTA",allocationSize=1)
@NamedQuery(name="ContaEmail.findConta", query="select c from ContaEmail c")
public class ContaEmail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_CONTA_EMAIL = "ContaEmail.findConta";
	
	@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="conta")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String hostname;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String porta;
	
	@Column 
	private String smtp;
	
	@Column
	private String emailadmin;
	
	
	
	

	

	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContaEmail) {
			ContaEmail conta = (ContaEmail) obj;
			return conta.getId() == id;
		}
		return false;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailadmin() {
		return emailadmin;
	}

	public void setEmailadmin(String emailadmin) {
		this.emailadmin = emailadmin;
	}
	

}
