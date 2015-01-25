package br.com.allisson.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.allisson.dao.DocumentoDAO;
import br.com.allisson.facade.ComprovanteEntregaFacade;
import br.com.allisson.modelo.ComprovanteEntrega;
import br.com.allisson.modelo.Documento;
import br.com.allisson.modelo.HistoricoNf;

@ManagedBean(name = "documentosBean")
@ViewScoped
public class DocumentosBean {

	private Documento documento;
	private HistoricoNf historiconf;
	private DocumentoDAO documentoDao;
	private List<Documento> documentos;
	private List<HistoricoNf> historicosnf;
	

	private Date dataInicio;
	private Date dataTermino;
	private String cliente;
	private Integer notafiscal;

	private String cpf_cnpj;

	private String nome;

	/*
	 * Comprovante de Entrega
	 */

	private ComprovanteEntrega foto;
	private ComprovanteEntregaFacade fotoFacade;
	private Documento documentoSelecionado;

	/*
	 * 
	 */

	public DocumentosBean() {
		documentoDao = new DocumentoDAO();
		fotoFacade = new ComprovanteEntregaFacade();
		
		documentos = documentoDao.listaNotasemAberto();
	}
	
	public void onTabChange(TabChangeEvent event){
		if (event.getTab().getId().equals("notasEmAberto")){
			documentos = documentoDao.listaNotasemAberto();
		}
	}

	public void consultaPublicaPorCpf() throws IOException {
		// return "consultaPublicaCpf";

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put("documentosBean", new DocumentosBean());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("consultaPublicaCpf.jsf");

			// FacesContext.getCurrentInstance().getExternalContext().redirect("rastreamento");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public List<HistoricoNf> getOcorrencias() {

		return historicosnf;
	}

	public void documentosPorPeriodo() {
		String data1;// = "01/01/2000";
		String data2;// = "20/05/2013";

		// documentos = documentoDao.listaDeDocumentos("01/08/2012",
		// "20/01/2013");

		DateFormat dtOutput = new SimpleDateFormat("dd/MM/yyyy");
		data1 = dtOutput.format(this.dataInicio);
		data2 = dtOutput.format(this.dataTermino);

		System.out.println(data1);
		System.out.println(data2);

		if (this.documento == null) {
			documentos = documentoDao.listaDeDocumentos(data1, data2,
					this.cliente);
			listaFotoComprovante(documentos);
		}
		// return documentos;
	}

	public void documentosPorNotaFiscal() {
		System.out.println(this.notafiscal);
		documentos = documentoDao.listaPorNotaFiscal(this.notafiscal);
		listaFotoComprovante(documentos);
	}

	
	public void ConsultaPublica() {
		if (this.documentos == null) {

			documentos = documentoDao.listaConsultaPublica(this.cpf_cnpj);

		}
	}

	public Documento getDocumento() {
		return documento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getNotafiscal() {
		return notafiscal;
	}

	public void setNotafiscal(Integer notafiscal) {
		this.notafiscal = notafiscal;
	}

	public List<HistoricoNf> getHistoricosnf() {
		return historicosnf;
	}

	public void setHistoricosnf(List<HistoricoNf> historicosnf) {
		this.historicosnf = historicosnf;
	}

	public HistoricoNf getHistoriconf() {
		return historiconf;
	}

	public void setHistoriconf(HistoricoNf historiconf) {
		this.historiconf = historiconf;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	/*
	 * Imagens
	 */

	public void listaFotoComprovante(List<Documento> documentos) {

		try {
			ServletContext sContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			for (Documento c : documentos) {

				// System.out.println("Percorrendo a Lista - " + c.getNr_cto()
				// + c.getTemImagem());

				if (c.getTemImagem()) {

					documentoSelecionado = new Documento();
					documentoSelecionado.setDoc(c.getDoc());
					documentoSelecionado.setFil_orig(c.getFil_orig());
					documentoSelecionado.setNr_cto(c.getNr_cto());

					foto = fotoFacade.getComprovante(documentoSelecionado);

					File folder = new File(sContext.getRealPath("/temp"));

					System.out.println(folder);

					if (!folder.exists())
						folder.mkdirs();

					String nomeArquivo = foto.getNrdocumento() + ".jpg";
					String arquivo = sContext.getRealPath("/temp")
							+ File.separator + nomeArquivo;

					criaArquivo(foto.getFrente(), arquivo);

					nomeArquivo = foto.getNrdocumento() + "V" + ".jpg";
					arquivo = sContext.getRealPath("/temp") + File.separator
							+ nomeArquivo;

					criaArquivo(foto.getVerso(), arquivo);

				}
			}

			// documentoSelecionado = new Documento();
			// documentoSelecionado.setDoc("CT");
			// documentoSelecionado.setFil_orig("SAO");
			// documentoSelecionado.setNr_cto(3490);

		} catch (Exception ex) {
			ex.printStackTrace();
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

	public void setDocumentoSelecionado(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public Documento getDocumentoSelecionado() {
		return this.documentoSelecionado;
	}

	public ComprovanteEntrega getFoto() {
		return foto;
	}

	public void setFoto(ComprovanteEntrega foto) {
		this.foto = foto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private StreamedContent file;

	public StreamedContent getFile() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/temp/"
						+ documentoSelecionado.getNr_cto() + ".jpg");
		file = new DefaultStreamedContent(stream, "image/jpg",
				documentoSelecionado.getNr_cto() + ".jpg");
		return file;
	}

	public StreamedContent getFileVerso() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/temp/"
						+ documentoSelecionado.getNr_cto() + "V.jpg");
		file = new DefaultStreamedContent(stream, "image/jpg",
				documentoSelecionado.getNr_cto() + "V.jpg");
		return file;
	}

	public String acesso() {
		return "paginas/protected/defaultUser/documentos.jsf";
	}

	public void postProcessXLS(Object document) {

		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();  
		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
			HSSFCell cell = header.getCell(i);
			
			cell.setCellStyle(cellStyle);
		}

	}

}
