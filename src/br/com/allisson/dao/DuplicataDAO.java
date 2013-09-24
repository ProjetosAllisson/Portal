package br.com.allisson.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.allisson.modelo.Duplicata;
import br.com.allisson.modelo.User;

public class DuplicataDAO extends GenericDAO<Duplicata>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User usuario = new User();

	public DuplicataDAO(){
		super(Duplicata.class);
		this.usuario = usuario.DevolveUsuarioSessao();
	}
	
	public List<Duplicata> duplicatasEmAberto(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		
		parameters.put("cgc", usuario.getCliente().getCgc());
		
		return super.findAllResult(Duplicata.EM_ABERTO, parameters);
	}
	
	public List<Duplicata> duplicatasEmAbertoPorGrupoCliente(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("grupo", usuario.getCliente().getGrupoCliente().getGrupo());
		return super.findAllResult(Duplicata.EM_ABERTO_GRUPO, parameters);
	}
	

}
