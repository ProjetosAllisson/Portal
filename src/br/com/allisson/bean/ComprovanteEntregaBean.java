package br.com.allisson.bean;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.allisson.facade.ComprovanteEntregaFacade;
import br.com.allisson.modelo.ComprovanteEntrega;
import br.com.allisson.modelo.Documento;

@ManagedBean(name = "comprovanteEntregaBean")
@RequestScoped
public class ComprovanteEntregaBean {

	private ComprovanteEntrega comprovante;
	private ComprovanteEntregaFacade facade;
	private Documento documento;

	private Documento documentoSelecionado;
	
	private Boolean localizouComprovante;

	public ComprovanteEntrega getComprovante() throws SQLException {
		if (comprovante == null) {
			facade = new ComprovanteEntregaFacade();

			String doc = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("documento");

			String filial = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("filial");

			String nrdoc = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("nrdocumento");

			Integer inrdoc;

			//if (nrdoc.trim().equals("")) {
				System.out.println("String vazia");
				//inrdoc = Integer.parseInt(nrdoc);
			//}
			// 

			
				
				// Documento doc = (Documento)
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objeto");

			// Documento meuObjeto = (Documento)
			// FacesContext.getCurrentInstance()
			// .getExternalContext().getFlash().get("objeto");

			
			
			documento = new Documento();
			//documento.setDoc("CT");
			//documento.setFil_orig("SAO");
			//documento.setNr_cto(3490);

			if (documento != null) {
				//System.out.println("Localizando comprovante");
				this.localizouComprovante = false;
				return comprovante = facade.getComprovante(documento);
			}

		}

		this.localizouComprovante = false;
		return comprovante;
	}

	public Documento getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public Boolean getLocalizouComprovante() {
		return localizouComprovante;
	}

	

}
