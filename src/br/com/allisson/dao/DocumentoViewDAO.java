package br.com.allisson.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.allisson.modelo.DocumentoView;
import br.com.allisson.modelo.FiltroDocumento;
import br.com.allisson.modelo.User;

public class DocumentoViewDAO extends GenericDAO<DocumentoView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User usuario = new User();
	
	public DocumentoViewDAO(){
		super(DocumentoView.class);
		this.usuario = usuario.DevolveUsuarioSessao();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoView> filtrados(FiltroDocumento filtro){
		
		
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		//criteria.setFirstResult(filtro.getPrimeiroRegistro());
		//criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}

	private Criteria criarCriteriaParaFiltro(FiltroDocumento filtro) {
		
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(DocumentoView.class);
		
		Criterion remetente;
		Criterion destinatario;
		Criterion consignatario;
		
		Disjunction dis = Restrictions.disjunction();
		
		if (this.usuario.consultaPorGrupoCliente()){
			remetente     = Restrictions.eq("grupo_remetente", usuario.getCliente().getGrupoCliente().getGrupo());
			destinatario  = Restrictions.eq("grupo_destinatario", usuario.getCliente().getGrupoCliente().getGrupo());
		}else{
			remetente     = Restrictions.eq("cgc_remetente", usuario.getCliente().getCgc());
			destinatario  = Restrictions.eq("cgc_destinatario", usuario.getCliente().getCgc());
			consignatario = Restrictions.eq("cgc_pagador", usuario.getCliente().getCgc());
			dis.add(consignatario);		
		}
		
		dis.add(remetente);
		dis.add(destinatario);
		
		criteria.add(dis);
		
		
		
        int dias =90*-1;
		
        if (filtro.getDataInicio()==null && filtro.getDataTermino()==null && (filtro.getNota_fiscal()==null || filtro.getNota_fiscal().equals("")
        		&&(filtro.getCtrc()==null || filtro.getCtrc()==0))){
        	criteria.add(Restrictions.isNull("entrega"));
        	
        	
    //and    	(((d.saida_entrega is not null) or (d.chegada is  null)) or ((d.saida_entrega is null) and (d.chegada is  null)) )
        	
        	Criterion emDeposito;
        	Criterion emEntrega;
        	
        	
        	Disjunction transito = Restrictions.disjunction();
        	
        	emEntrega  = Restrictions.isNotNull("saida_entrega");
        	emDeposito = Restrictions.isNull("chegada");
        	transito.add(emEntrega);
        	transito.add(emDeposito);
        	
        	//LogicalExpression orExp = Restrictions.or(emEntrega, emDeposito);
        	
        	criteria.add(transito).add(Restrictions.isNotNull("embarque"));
        	
        }
		
		Calendar dt_partida = Calendar.getInstance();
		dt_partida.setTime(new java.util.Date());
		dt_partida.add(Calendar.DAY_OF_MONTH, dias);
		
		if (filtro.getDataInicio()!=null){
			dt_partida.setTime(filtro.getDataInicio());
		}
		criteria.add(Restrictions.ge("emissao", dt_partida));
		
		
		if (filtro.getDataTermino()!=null){
			Calendar dt_termino = Calendar.getInstance();
			dt_termino.setTime(filtro.getDataTermino());
			criteria.add(Restrictions.le("emissao", dt_termino));
		}
		
		
		if (filtro.getNota_fiscal()!=null && !filtro.getNota_fiscal().equals("")){
			criteria.add(Restrictions.like("id.n_fiscal", "%"+filtro.getNota_fiscal()+"%"));
		}
		
		if (filtro.getCtrc()!=null && filtro.getCtrc()>0){
			criteria.add(Restrictions.eq("ctrc", filtro.getCtrc()));
		}
		
		//criteria.addOrder(Order.desc("emissao"));
		
		//if (StringUtils.isNotEmpty(filtro.getDescricao())) {
			//criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		//}
		return criteria;
	}

	public int quantidadeFiltrados(FiltroDocumento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	

}
