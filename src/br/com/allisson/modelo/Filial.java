package br.com.allisson.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STWOPETFIL")
public class Filial {
	
	@Id
	private FilialPk id;
	
	

	public FilialPk getId() {
		return id;
	}

	public void setId(FilialPk id) {
		this.id = id;
	}
	
	

	
}
