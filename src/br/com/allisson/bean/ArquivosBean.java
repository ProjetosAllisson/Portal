package br.com.allisson.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import br.com.allisson.util.JSFMessageUtil;
import br.com.allisson.util.OperacoesArquivos;

@ManagedBean(name = "arquivosBean")
public class ArquivosBean extends AbstractMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Array de bytes que armazenará o conteúdo do arquivo PDF
	private byte[] conteudo;

	// Caminho completo do arquivo informado pelo usuário.
	// Ex: C:\Meus Documentos\boletim.pdf
	private String caminho;// = "c:/Portal/clickOn.pdf";
	private String nomearquivo;
	private String extensao;
	
	private String eErro;
	
	public void download(String caminho_arquivo, String nome_arquivo,String extensao_arquivo){
		
		this.caminho     = caminho_arquivo;
		this.nomearquivo = nome_arquivo; 
		this.extensao    = "."+extensao_arquivo;
		
		//OperacoesArquivos.downloadFile(nome_arquivo+"."+extensao_arquivo, caminho_arquivo, "xml", FacesContext.getCurrentInstance());
		
        // deve ser passado o nome do arquivo+extensão  teste.txt
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		
		ExternalContext context = facesContext.getExternalContext(); // Context
				
		File file = new File(caminho_arquivo + nome_arquivo+extensao); // LINHA ALTERADA
		
		try {
			FileInputStream in = new FileInputStream(file);
			
			
			ServletContext sContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			File folder = new File(sContext.getRealPath("/temp"));

			if (!folder.exists())
				folder.mkdirs();
			
			String arquivoSaida = sContext.getRealPath("/temp") + File.separator
					+ nome_arquivo+extensao;
			
			File file2 = new File (arquivoSaida); 
			FileOutputStream saida = new FileOutputStream(file2);
			
			byte[] buf = new byte[(int) file.length()];
			int count;
			while ((count = in.read(buf)) >= 0) {
				saida.write(buf, 0, count);
			}
			file = new File(arquivoSaida); // LINHA ALTERADA
			in.close();
			in = new FileInputStream(file);
			
			HttpServletResponse response = (HttpServletResponse) context
					.getResponse();
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ nome_arquivo+extensao + "\""); // aki eu seto o header e o nome q vai
										// aparecer na hr do donwload
			response.setContentLength((int) file.length()); // O tamanho do arquivo
			response.setContentType(extensao_arquivo); // e obviamente o tipo
			
			OutputStream out = response.getOutputStream();
			buf = new byte[(int) file.length()];
			
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			in.close();
			
			saida.close();
			out.flush();
			out.close();
			facesContext.responseComplete();
		} catch (Exception ex) {
			System.out.println("Error in downloadFile: " + ex.getMessage());
			JSFMessageUtil messageUtil = new JSFMessageUtil();
			messageUtil.sendErrorMessageToUser(ex.getMessage());
		}
		
		
		
		
		
		
		/*
		if (this.importarArquivo()){
			 this.processaArquivo(true);
		}else {
			 displayErrorMessageToUser(eErro);
		}
		*/
		
		
	}
	
	
	
	
	private boolean importarArquivo() {
		try {
			// Cria um objeto File a partir do caminho especificado

			
			//String caminho = configSatwinfacade.leclausula("FATURAMENTO","Path para Salvar Boletos em PDF", duplicata.getId().getFil_orig());
			//"c:/portal/"
			//arquivo = caminho+'/'+ Geral.LimpaString(duplicata.getCliente().getCgc(), "[./-]") +
			  //        String.format("%06d", duplicata.getId().getFatura()) + ".pdf";
			
			
			//nomearquivo = Geral.LimpaString(duplicata.getCliente().getCgc(), "[./-]") +
			  //        String.format("%06d", duplicata.getId().getFatura()) + ".pdf";

			String arquivo = caminho+nomearquivo+'.'+extensao;
			System.out.println(arquivo);

			File file = new File(arquivo);

			// Inicializa o array bytes com o tamanho do arquivo especificado.
			// Note a conversao para int, restringindo a capacidade maxima do
			// arquivo em 2 GB
			conteudo = new byte[(int) file.length()];

			// Cria um InputStream a partir do objeto File
			InputStream is = new FileInputStream(file);

			// Aqui o InputStream faz a leitura do arquivo, transformando em um
			// array de bytes
			
			is.read(conteudo);

			// Fecha o InputStream, liberando seus recursos
			is.close();
			return true;
		} catch (Exception e) {
			eErro = e.getMessage();
			return false;
		}
	}
	
	
	private void processaArquivo(boolean download) {

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
		response.setContentType("application/"+extensao);

		// Seta o tamanho do conteudo no cabecalho da resposta. No caso, o
		// tamanho
		// em bytes do pdf
		response.setContentLength(conteudo.length);

		// Seta o nome do arquivo e a disposição: "inline" abre no próprio
		// navegador.
		// Mude para "attachment" para indicar que deve ser feito um download

		if (download) {
			response.setHeader("Content-disposition",
					"attachment; filename="+nomearquivo+"."+extensao);
		} else {
			response.setHeader("Content-disposition",
					"inline; filename="+nomearquivo);
		}
		try {

			// Envia o conteudo do arquivo PDF para o response
			response.getOutputStream().write(conteudo);

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
