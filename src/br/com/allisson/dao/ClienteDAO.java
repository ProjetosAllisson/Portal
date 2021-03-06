package br.com.allisson.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.allisson.modelo.Cliente;


public class ClienteDAO extends GenericDAO<Cliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1817413732492918799L;
	
	public ClienteDAO(){
		super(Cliente.class);
	}
	
	public Cliente findCliente(String cnpj){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cgc", cnpj);
		
		return super.findOneResult(Cliente.FIND_CLIENTE, parameters);
	}
	
	public List<Cliente> allClientes() {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String strDateFrom = "2013/01/25";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date dateFrom = null;
		try {
			dateFrom = formatter.parse(strDateFrom);
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}

        String strDateTo = "2018/11/02";
        Date dateTo = null;
		try {
			dateTo = formatter.parse(strDateTo);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
        
        parameters.put("start", dateFrom);
        parameters.put("end", dateTo);
		
		return super.findAllResult(Cliente.ALL_CLIENTES, parameters);
		
	}
	
	public List<Cliente> allClientesPorNome(String nome, Integer grupo){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		if (grupo > 0){
			parameters.put("nome", nome+"%");
			parameters.put("grupo", grupo);
			
			return super.findAllResult(Cliente.POR_NOME_GRUPO, parameters);
		}
		
		
		if (nome.equals("")){
			return this.allClientes();
		}
		parameters.put("nome", nome+"%");
		
		
		return super.findAllResult(Cliente.POR_NOME_CLIENTES, parameters);
	}

}
