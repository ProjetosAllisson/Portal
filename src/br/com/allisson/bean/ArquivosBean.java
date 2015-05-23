package br.com.allisson.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

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
	
	public void download(String caminho_arquivo, String nome_arquivo,String extensao_arquivo){
		
		this.caminho     = caminho_arquivo;
		this.nomearquivo = nome_arquivo; 
		this.extensao    = extensao_arquivo;
		
		if (this.importarArquivo()){
			this.processaArquivo(true);
		}else {
			displayErrorMessageToUser("Arquivo "+caminho_arquivo+nome_arquivo+"."+extensao_arquivo +" não encontrado.");
		}
		
		
		
		
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

			String arquivo = caminho+'/'+nomearquivo+'.'+extensao;
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
			e.printStackTrace();
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
