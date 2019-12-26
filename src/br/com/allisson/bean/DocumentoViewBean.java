package br.com.allisson.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.allisson.facade.DocumentoViewFacade;
import br.com.allisson.modelo.DocumentoView;
import br.com.allisson.modelo.FiltroDocumento;
import br.com.allisson.modelo.User;
import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.Formatter;

@ManagedBean(name = "docsViewBean")
@ViewScoped
public class DocumentoViewBean {

	private FiltroDocumento filtro;
	//private LazyDataModel<DocumentoView> model;

	private DocumentoViewFacade docViewFacade = new DocumentoViewFacade();
	private DocumentoView documentoSelecionado;

	private List<DocumentoView> documentos = new ArrayList<DocumentoView>();
	
	private User usuario = new User();

	private String paramCpnj_Cpf;
	private String paramNotaFiscal;
	
	private boolean lChaveAcessoObrigatoria=true;
	private boolean lDadosNotaObrigatoria;

	/*
	 * public DocumentoViewBean() { model = new LazyDataModel<DocumentoView>() {
	 * 
	 * /**
	 */
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public List<DocumentoView> load(int first, int pageSize, String
	 * sortField, SortOrder sortOrder, Map<String, String> filters) {
	 * 
	 * getFiltro().setPrimeiroRegistro(first);
	 * getFiltro().setQuantidadeRegistros(pageSize);
	 * getFiltro().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
	 * getFiltro().setPropriedadeOrdenacao(sortField);
	 * 
	 * docViewDao.beginTransaction();
	 * setRowCount(docViewDao.quantidadeFiltrados(getFiltro()));
	 * 
	 * return docViewDao.filtrados(getFiltro()); }
	 * 
	 * };
	 * 
	 * }
	 */

	@PostConstruct
	public void init() {
		filtro = new FiltroDocumento();
		this.usuario = usuario.DevolveUsuarioSessao();
		
	}

	public void onTabChange(TabChangeEvent event) {
		filtro = new FiltroDocumento();
		documentos = new ArrayList<DocumentoView>();
		if (!event.getTab().getId().equals("notasEmAberto")) {
			// filtro.setDataInicio(new Date());
		}
		
		if (event.getTab().getId().equals("consultaCliente")) {
			//filtro.setPesquisaAdmin(true);
			//filtro.setClienteSelecionado(this.usuario.getCliente());
			//filtro.setCnpj_cpf(this.usuario.getCliente().getCgc());
		}
		
	}
	
	public void onTabChangeConsultaPublica(TabChangeEvent event) {
		setlChaveAcessoObrigatoria(false);
		setlDadosNotaObrigatoria(false);
		
		if (event.getTab().getId().equals("porChaveAcesso")) {
			setlChaveAcessoObrigatoria(true);
		}
		
		if (event.getTab().getId().equals("porNotaFiscal")) {
			setlDadosNotaObrigatoria(true);
		}
		
	}

	public void pesquisar() {
		
		if ((this.usuario!=null) && (this.usuario.isAdmin())) {
			filtro.setPesquisaAdmin(true);
		}
		documentos = docViewFacade.consultaDocumentos(getFiltro());
	}
	
	public void pesquisarPublica() {
		
		documentos = new ArrayList<DocumentoView>();
		
		if (islChaveAcessoObrigatoria()) {
			
			if (getFiltro().getChave_acesso().equals("")) {
				FacesContext.getCurrentInstance().addMessage("msgValidacao", 
						new FacesMessage("Informe a chave de acesso."));
				return;
			}
			
			if (getFiltro().getChave_acesso().length()<44) {
				FacesContext.getCurrentInstance().addMessage("msgValidacao", 
						new FacesMessage("Chave de acesso inválida."));
				return;
			}
			
			//getFiltro().setNota_fiscal();
			//System.out.println(getFiltro().getChave_acesso());
			String cnpj_cpf = String.valueOf(getFiltro().getChave_acesso().toCharArray(),6,14);
			
			Formatter cnpj = new CNPJFormatter();
			cnpj_cpf = cnpj.format(cnpj_cpf);
			getFiltro().setCnpj_cpf(cnpj_cpf);
			
			//System.out.println(cnpj_cpf);
			
			String nota = String.valueOf(getFiltro().getChave_acesso().toCharArray(), 25,9);
			//System.out.println(Integer.valueOf(nota));
			
			//nota = Integer.toString(Integer.valueOf(nota));
			
			getFiltro().setNota_fiscal(Integer.toString(Integer.valueOf(nota)));
			
		}
		
		if (islDadosNotaObrigatoria()) {
			
			if (getFiltro().getCnpj_cpf().equals("")) {
				FacesContext.getCurrentInstance().addMessage("msgValidacao", 
				new FacesMessage("Informe o Cpf/Cnpj."));
				
				return;
			}
			
			if (getFiltro().getNota_fiscal().equals("")) {
				FacesContext.getCurrentInstance().addMessage("msgValidacao", 
				new FacesMessage("Informe a Nota Fiscal"));
				
				return;
			}
			
			
		}
		
		documentos = docViewFacade.consultaDocumentos(getFiltro());
	}
	
	
	public void notasEmAberto() {
		filtro.setPesquisaAdmin(false);
		documentos = docViewFacade.consultaDocumentos(getFiltro());
	}
	
	
	public void consultaPublica() {
		getFiltro().setConsultaPublica(true);
	}

	public Date getMinDataInicio() {
		int iDias = 90 * -1;
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, iDias);
		return data.getTime();
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

		nomeArquivo = documentoSelecionado.getId().getDocumento() + "V"
				+ ".jpg";
		arquivo = sContext.getRealPath("/temp") + File.separator + nomeArquivo;

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

	

	public DocumentoView getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoView documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public void setFiltro(FiltroDocumento filtro) {
		this.filtro = filtro;
	}

	public List<DocumentoView> getDocumentos() {
		return documentos;
	}

	public String getParamCpnj_Cpf() {
		return paramCpnj_Cpf;
	}

	public void setParamCpnj_Cpf(String paramCpnj_Cpf) {
		this.paramCpnj_Cpf = paramCpnj_Cpf;
	}

	public String getParamNotaFiscal() {
		return paramNotaFiscal;
	}

	public void setParamNotaFiscal(String paramNotaFiscal) {
		this.paramNotaFiscal = paramNotaFiscal;
		
		
		System.out.println("Notaaa  .."+this.paramNotaFiscal);
		
		if (this.getParamCpnj_Cpf() != null
				&& this.getParamNotaFiscal() != null
				&& !this.getParamCpnj_Cpf().equals("")
				&& !this.getParamNotaFiscal().equals("")) {
			
			filtro = new FiltroDocumento();
			filtro.setCnpj_cpf(this.getParamCpnj_Cpf());
			filtro.setNota_fiscal(this.getParamNotaFiscal());
			this.pesquisar();
			
		}
		
		
	}

	private boolean islChaveAcessoObrigatoria() {
		return lChaveAcessoObrigatoria;
	}

	private void setlChaveAcessoObrigatoria(boolean lChaveAcessoObrigatoria) {
		this.lChaveAcessoObrigatoria = lChaveAcessoObrigatoria;
	}

	private boolean islDadosNotaObrigatoria() {
		return lDadosNotaObrigatoria;
	}

	private void setlDadosNotaObrigatoria(boolean lDadosNotaObrigatoria) {
		this.lDadosNotaObrigatoria = lDadosNotaObrigatoria;
	}

}
