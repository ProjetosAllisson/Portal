package br.com.allisson.modelo.coleta;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TBL_VEICULO")
@NamedQueries({
	@NamedQuery(name="Veiculo.findAll", query="select v from Veiculo v")
})
public class Veiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Veiculo.findAll";
	
	@Id
	private VeiculoPk id;
	
	@Column
	private String veiculo;
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Veiculo) {
			Veiculo veic = (Veiculo) obj;
			return veic.getId() == getId();
		}
		return false;
	}

	public VeiculoPk getId() {
		return id;
	}

	public void setId(VeiculoPk id) {
		this.id = id;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

}
