package br.com.allisson.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.allisson.util.Criptografia;

@Entity
@Table(name = "TBL_SERVICOS")
@NamedQuery(name= "ServicosDisponiveis.findServicos", query = "select u from ServicosDisponiveis u")
public class ServicosDisponiveis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_SERVICOS = "ServicosDisponiveis.findServicos";
	
	@Transient
	private String criptografado;

	@Transient
	private Criptografia criptografia = new Criptografia();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String rastreamento;
	private String comprovanteEntrega;

	public String getRastreamento() {
		return rastreamento;
	}

	public void setRastreamento(String rastreamento) {
		this.criptografado = Criptografia.md5(rastreamento);
		this.rastreamento = this.criptografado;

	}

	public String getComprovanteEntrega() {
		return comprovanteEntrega;
	}

	public void setComprovanteEntrega(String comprovanteEntrega) {
		this.criptografado = Criptografia.md5(comprovanteEntrega);
		this.comprovanteEntrega = this.criptografado;
	}

}
