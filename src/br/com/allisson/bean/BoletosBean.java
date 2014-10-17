package br.com.allisson.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.allisson.facade.ConfigSatwinFacade;
import br.com.allisson.modelo.Duplicata;
import br.com.allisson.util.Geral;

@ManagedBean(name = "boletosBean")
public class BoletosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String INJECTION_BOLETO = "#{boletosBean}";

	private Duplicata duplicata;
	private ConfigSatwinFacade configSatwinfacade = new ConfigSatwinFacade();

	// Array de bytes que armazenará o conteúdo do arquivo PDF
	private byte[] conteudoPdf;

	// Caminho completo do arquivo informado pelo usuário.
	// Ex: C:\Meus Documentos\boletim.pdf
	private String arquivo;// = "c:/Portal/clickOn.pdf";
	private String nomearquivo;

	private void importarArquivo() {
		try {
			// Cria um objeto File a partir do caminho especificado

			System.out.println(duplicata.getId().getFil_orig());
			
			String caminho = configSatwinfacade.leclausula("FATURAMENTO","Path para Salvar Boletos em PDF", duplicata.getId().getFil_orig());
			//"c:/portal/"
			arquivo = caminho+'/'+ Geral.LimpaString(duplicata.getCliente().getCgc(), "[./-]") +
			          String.format("%06d", duplicata.getId().getFatura()) + ".pdf";
			
			
			nomearquivo = Geral.LimpaString(duplicata.getCliente().getCgc(), "[./-]") +
			          String.format("%06d", duplicata.getId().getFatura()) + ".pdf";

			System.out.println("Nome arquivo" + arquivo);

			File file = new File(arquivo);

			// Inicializa o array bytes com o tamanho do arquivo especificado.
			// Note a conversao para int, restringindo a capacidade maxima do
			// arquivo em 2 GB
			conteudoPdf = new byte[(int) file.length()];

			// Cria um InputStream a partir do objeto File
			InputStream is = new FileInputStream(file);

			// Aqui o InputStream faz a leitura do arquivo, transformando em um
			// array de bytes
			is.read(conteudoPdf);

			// Fecha o InputStream, liberando seus recursos
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String visualizarPdf() {

		this.importarArquivo();
		
		this.processaPDF(false);
		
		return "";

	}
	
	public String downloadPdf(){
		this.importarArquivo();
		
		this.processaPDF(true);
		
		return "";
	}

	public Duplicata getDuplicata() {
		return duplicata;
	}

	public void setDuplicata(Duplicata duplicata) {
		this.duplicata = duplicata;
	}

	private void processaPDF(boolean download) {

		FacesContext fc = FacesContext.getCurrentInstance();

		// Obtem o HttpServletResponse, objeto responsável pela resposta do
		// servidor ao browser
		HttpServletResponse response = (HttpServletResponse) fc
				.getExternalContext().getResponse();

		// Limpa o buffer do response
		response.reset();

		// Seta o tipo de conteudo no cabecalho da resposta. No caso, indica que
		// o
		// conteudo sera um documento pdf.
		response.setContentType("application/pdf");

		// Seta o tamanho do conteudo no cabecalho da resposta. No caso, o
		// tamanho
		// em bytes do pdf
		response.setContentLength(conteudoPdf.length);

		// Seta o nome do arquivo e a disposição: "inline" abre no próprio
		// navegador.
		// Mude para "attachment" para indicar que deve ser feito um download

		if (download) {
			response.setHeader("Content-disposition",
					"attachment; filename="+nomearquivo);
		} else {
			response.setHeader("Content-disposition",
					"inline; filename="+nomearquivo);
		}
		try {

			// Envia o conteudo do arquivo PDF para o response
			response.getOutputStream().write(conteudoPdf);

			// Descarrega o conteudo do stream, forçando a escrita de qualquer
			// byte
			// ainda em buffer
			response.getOutputStream().flush();

			// Fecha o stream, liberando seus recursos
			response.getOutputStream().close();

			// Sinaliza ao JSF que a resposta HTTP para este pedido já foi
			// gerada
			fc.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
