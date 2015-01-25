package br.com.allisson.modelo.coleta;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_COLETA_ITENS")
public class ColetaItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ColetaItens", sequenceName="ColItens_Seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ColetaItens")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="coleta_id")
	private Coleta coleta;
	
	@Column
	private String especie;
	
	@Column
	private int notaFiscal;
	
	@Column
	private BigDecimal vlrNotaFiscal;
	
	@Column
	private int altura;
	
	@Column
	private int comprimento;
	
	@Column
	private int largura;
	
	@Column
	private int quantidade;
	
	@Column
	private int volumes;
	
	@Column
	private BigDecimal kgsReal;
	
	//@Column
	//private BigDecimal fatorM3;
	
	@Column
	private BigDecimal kgsCubado;
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public BigDecimal getVlrNotaFiscal() {
		return vlrNotaFiscal;
	}

	public void setVlrNotaFiscal(BigDecimal vlrNotaFiscal) {
		this.vlrNotaFiscal = vlrNotaFiscal;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getComprimento() {
		return comprimento;
	}

	public void setComprimento(int comprimento) {
		this.comprimento = comprimento;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getVolumes() {
		return volumes;
	}

	public void setVolumes(int volumes) {
		this.volumes = volumes;
	}

	public BigDecimal getKgsReal() {
		return kgsReal;
	}

	public void setKgsReal(BigDecimal kgsReal) {
		this.kgsReal = kgsReal;
	}

	//public BigDecimal getFatorM3() {
		//return fatorM3;
	//}

	//public void setFatorM3(BigDecimal fatorM3) {
	//	this.fatorM3 = fatorM3;
//	}

	public BigDecimal getKgsCubado() {
		return kgsCubado;
	}

	public void setKgsCubado(BigDecimal kgsCubado) {
		this.kgsCubado = kgsCubado;
	}

	public int getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(int notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ColetaItem))
			return false;
		
		ColetaItem item = (ColetaItem) obj;
		
		return (item.getNotaFiscal()==notaFiscal);
		
		
	}

}
