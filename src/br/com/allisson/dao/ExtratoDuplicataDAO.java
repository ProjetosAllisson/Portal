package br.com.allisson.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.allisson.modelo.ExtratoDuplicata;
import br.com.allisson.modelo.FiltroExtratoDuplicata;
import br.com.allisson.modelo.User;

public class ExtratoDuplicataDAO extends GenericDAO<ExtratoDuplicata>  {

	
	private static final long serialVersionUID = 1L;
	
	private User usuario = new User();
	
	
	public ExtratoDuplicataDAO() {
		super(ExtratoDuplicata.class);
		this.usuario = usuario.DevolveUsuarioSessao();	
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtratoDuplicata> extratoDuplicatasEmitidas(FiltroExtratoDuplicata filtro ){
		
		Criteria criteria = criaCriteriaParaFiltro(filtro);
		return criteria.list();
	}

	private Criteria criaCriteriaParaFiltro(FiltroExtratoDuplicata filtro) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ExtratoDuplicata.class);
		
		if (this.usuario.consultaPorGrupoCliente()){
			criteria.add(Restrictions.eq("grupo_pagador", this.usuario.getCliente().getGrupoCliente().getGrupo()));
		}else {
			criteria.add(Restrictions.eq("cgc_pagador", this.usuario.getCliente().getCgc()));	
		}
		
		
		
		if ((filtro.getDataInicio()!=null)&&(filtro.getDataTermino()!=null)){
			
			Calendar data = Calendar.getInstance();
			
			data.setTime(filtro.getDataInicio());
			criteria.add(Restrictions.ge("dt_cte", data));
			
			Calendar dt_termino = Calendar.getInstance();
			dt_termino.setTime(filtro.getDataTermino());	
			criteria.add(Restrictions.le("dt_cte", dt_termino));
			
			criteria.addOrder(Order.asc("dt_cte"));
			
			
		}
		
		
		return criteria;
	}

	
	
	

}
