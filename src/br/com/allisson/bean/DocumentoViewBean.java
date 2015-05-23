package br.com.allisson.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;

import br.com.allisson.dao.DocumentoViewDAO;
import br.com.allisson.modelo.DocumentoView;
import br.com.allisson.modelo.FiltroDocumento;

@ManagedBean(name = "docsViewBean")
@ViewScoped
public class DocumentoViewBean {

	private FiltroDocumento filtro = new FiltroDocumento();
	private LazyDataModel<DocumentoView> model;

	private DocumentoViewDAO docViewDao = new DocumentoViewDAO();
	private DocumentoView documentoSelecionado;

	public DocumentoViewBean() {
		model = new LazyDataModel<DocumentoView>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public List<DocumentoView> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {

				getFiltro().setPrimeiroRegistro(first);
				getFiltro().setQuantidadeRegistros(pageSize);
				getFiltro().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				getFiltro().setPropriedadeOrdenacao(sortField);

				docViewDao.beginTransaction();
				setRowCount(docViewDao.quantidadeFiltrados(getFiltro()));
				
				return docViewDao.filtrados(getFiltro());
			}

		};

	}
	
	public void onTabChange(TabChangeEvent event){
		filtro = new FiltroDocumento();
		if (!event.getTab().getId().equals("notasEmAberto")){
			//filtro.setDataInicio(new Date());
		}
	}

	public void exibeComprovante() {

		ServletContext sContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();

		File folder = new File(sContext.getRealPath("/temp"));

		
		if (!folder.exists())
			folder.mkdirs();

		String nomeArquivo = documentoSelecionado.getId().getDocumento()
				+ ".jpg";
		String arquivo = sContext.getRealPath("/temp") + File.separator
				+ nomeArquivo;

		if (documentoSelecionado.getImg_comprovante_frente() != null) {
			criaArquivo(documentoSelecionado.getImg_comprovante_frente(),
					arquivo);
		}
		
		nomeArquivo = documentoSelecionado.getId().getDocumento() + "V" + ".jpg";
		arquivo = sContext.getRealPath("/temp") + File.separator
				+ nomeArquivo;


		
		if (documentoSelecionado.getImg_comprovante_verso() != null) {
			System.out.println("Verso");
			criaArquivo(documentoSelecionado.getImg_comprovante_verso(),
					arquivo);
		}

		
	}

	private void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);

			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private StreamedContent file;

	public StreamedContent getFile() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/temp/"
						+ documentoSelecionado.getId().getDocumento() + ".jpg");
		file = new DefaultStreamedContent(stream, "image/jpg",
				documentoSelecionado.getId().getDocumento() + ".jpg");
		return file;
	}

	public StreamedContent getFileVerso() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/temp/"
						+ documentoSelecionado.getId().getDocumento() + "V.jpg");
		file = new DefaultStreamedContent(stream, "image/jpg",
				documentoSelecionado.getId().getDocumento() + "V.jpg");
		return file;
	}

	public FiltroDocumento getFiltro() {
		return filtro;
	}

	public LazyDataModel<DocumentoView> getModel() {
		return model;
	}

	public DocumentoView getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoView documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public void setFiltro(FiltroDocumento filtro) {
		this.filtro = filtro;
	}

}
