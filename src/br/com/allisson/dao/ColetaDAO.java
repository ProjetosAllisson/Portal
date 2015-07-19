package br.com.allisson.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.com.allisson.modelo.User;
import br.com.allisson.modelo.coleta.Coleta;
import br.com.allisson.modelo.coleta.ColetaAutorizadaEnum;
import br.com.allisson.modelo.coleta.ColetaStatusEnum;
import br.com.allisson.modelo.coleta.vo.DataValor;
import br.com.allisson.modelo.coleta.vo.UserValor;

public class ColetaDAO extends GenericDAO<Coleta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Criteria criteria;

	public ColetaDAO() {
		super(Coleta.class);
	}

	public List<Coleta> findAllColeta() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		User user = new User();
		user = user.DevolveUsuarioSessao();

		parameters.put("user", user);

		if (user.isAdmin()) {
			return super.findAllResult(Coleta.FIND_ALL, null);
		}

		return super.findAllResult(Coleta.FIND_ALL_USER, parameters);
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorDate(Integer numeroDias,
			User criadoPor) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Coleta.class);

		numeroDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDias,
				dataInicial);

		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.sqlGroupProjection(
						"cast(diapedido as date) as data", "diapedido",
						new String[] { "data" },
						new Type[] { StandardBasicTypes.DATE }))
				.add(Projections.sum("vlrCobrado").as("valor")));

		if (criadoPor != null) {
			criteria.add(Restrictions.eq("user", criadoPor));
		}

		criteria.add(Restrictions.eq("autorizada", ColetaAutorizadaEnum.SIM));
		criteria.add(Restrictions
				.eq("situacaoColeta", ColetaStatusEnum.EMITIDO));

		/*
		 * List<Object> valores = criteria.list();
		 * 
		 * for (Object r : valores) { Object[] row = (Object[]) r;
		 * 
		 * System.out.println(row[0]); System.out.println(row[1]); }
		 */
		List<DataValor> valoresPorData = criteria.setResultTransformer(
				Transformers.aliasToBean(DataValor.class)).list();

		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}

		// select date(emissao) as data, sum(vlrCobrado) as valor from tabela
		return resultado;

	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<Date, BigDecimal>();

		for (int i = 0; i <= numeroDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		return mapaInicial;
	}

	public Map<String, BigDecimal> valoresPorUsuario(Integer numeroDias,
			User criadoPor) {
		Session session = em.unwrap(Session.class);
		setCriteria(session.createCriteria(Coleta.class));

		Map<String, BigDecimal> resultado = new TreeMap<String, BigDecimal>();

		numeroDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDias * -1);

		getCriteria().setProjection(
				Projections.projectionList()
						.add(Projections.groupProperty("user").as("usuario"))
						.add(Projections.sum("vlrCobrado").as("valor")));

		
		/*
		List<Object> valores = getCriteria().list();

		for (Object r : valores) {
			Object[] row = (Object[]) r;

			System.out.println(row[0]);
			System.out.println(row[1]);
		}
		*/

		@SuppressWarnings("unchecked")
		List<UserValor> valoresPorUsuario = criteria.setResultTransformer(
				Transformers.aliasToBean(UserValor.class)).list();
		
		for (UserValor userValor : valoresPorUsuario) {
			resultado.put(userValor.getUsuario().getLogin(), userValor.getValor());
		}

		// Map<String, BigDecimal> resultado = criarMapaValorVazioPorUsuario();

		// for (String user : resultado.keySet()) {
		// System.out.println(user+"   "+resultado.get(user));
		// }

		return resultado;

	}

	/*
	private Map<String, BigDecimal> criarMapaValorVazioPorUsuario() {
		UserFacade userFacade = new UserFacade();
		Map<String, BigDecimal> mapaInicial = new TreeMap<String, BigDecimal>();

		for (User user : userFacade.listAll()) {
			mapaInicial.put(user.getLogin(), BigDecimal.ZERO);
		}

		return null;
	}

	*/
	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
