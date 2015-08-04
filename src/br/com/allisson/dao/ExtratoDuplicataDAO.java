package br.com.allisson.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.allisson.modelo.ExtratoDuplicata;
import br.com.allisson.modelo.FiltroDocumento;
import br.com.allisson.modelo.User;

public class ExtratoDuplicataDAO extends GenericDAO<ExtratoDuplicata>  {

	
	private static final long serialVersionUID = 1L;
	
	private User usuario = new User();
	
	
	public ExtratoDuplicataDAO() {
		super(ExtratoDuplicata.class);
		//this.usuario = usuario.DevolveUsuarioSessao();	
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtratoDuplicata> extratoDuplicatasEmitidas(FiltroDocumento filtro ){
		
		Criteria criteria = criaCriteriaParaFiltro(filtro);
		return criteria.list();
	}

	private Criteria criaCriteriaParaFiltro(FiltroDocumento filtro) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ExtratoDuplicata.class);
		
		//criteria.add(Restrictions.eq(propertyName, value))
		
		
		if ((filtro.getDataInicio()!=null)&&(filtro.getDataTermino()!=null)){
			
			Calendar data = Calendar.getInstance();
			
			data.setTime(filtro.getDataInicio());
			criteria.add(Restrictions.ge("dt_fatura", data));
			
			data.setTime(filtro.getDataTermino());
			criteria.add(Restrictions.le("dt_fatura", data));
			
		}
		
		
		return criteria;
	}

	
	
	

}
