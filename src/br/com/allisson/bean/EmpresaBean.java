package br.com.allisson.bean;

import java.io.ByteArrayInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.allisson.dao.EmpresaDAO;
import br.com.allisson.modelo.Empresa;

@ManagedBean(name = "empresaBean")
@SessionScoped
public class EmpresaBean {
	
	Empresa empresa;
	EmpresaDAO empresaDao;
	
	private StreamedContent imagem;
	
	public EmpresaBean(){
	
		empresaDao = new EmpresaDAO();
		empresa = new Empresa();
	}
	
	public Empresa getEmpresa(){
		return empresa = empresaDao.DadosTransportadora();
	}
	

	
	public StreamedContent getImagem(){  
		imagem = new DefaultStreamedContent(new ByteArrayInputStream(empresa.getLogo()));
		return imagem;
          
    }  
	

	
	
	

}
