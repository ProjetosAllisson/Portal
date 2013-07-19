package br.com.allisson.modelo;

import java.io.ByteArrayInputStream;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ComprovanteEntrega {

	private String documento;
	private String filial;
	private Integer nrdocumento;

	private byte[] frente;
	private byte[] verso;

	private StreamedContent imagem;
	private StreamedContent imagemverso;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public Integer getNrdocumento() {
		return nrdocumento;
	}

	public void setNrdocumento(Integer nrdocumento) {
		this.nrdocumento = nrdocumento;
	}

	public byte[] getFrente() {
		return frente;
	}

	public void setFrente(byte[] frente) {
		this.frente = frente;
	}

	public byte[] getVerso() {
		return verso;
	}

	public void setVerso(byte[] verso) {
		this.verso = verso;
	}

	public StreamedContent getImagem() {
		imagem = new DefaultStreamedContent(new ByteArrayInputStream(
				getFrente()));

		if (imagem == null) {

			imagem = new DefaultStreamedContent(FacesContext
					.getCurrentInstance().getExternalContext()
					.getResourceAsStream("//resources//images//profile.gif"));

		}

		return imagem;

	}

	public StreamedContent getImagemVerso() {
		imagemverso = new DefaultStreamedContent(new ByteArrayInputStream(
				getVerso()));

		if (imagemverso == null) {

			imagemverso = new DefaultStreamedContent(FacesContext
					.getCurrentInstance().getExternalContext()
					.getResourceAsStream("//resources//images//profile.gif"));

		}

		return imagemverso;
	}

}
