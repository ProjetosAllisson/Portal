package br.com.allisson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.allisson.factory.ConnectionFactory;
import br.com.allisson.modelo.HistoricoNf;

public class HistoricoDAO {

	private final Connection connection;

	public HistoricoDAO() {
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<HistoricoNf> listaHistoricoNf(String cgc, String serie, Long n_fiscal,
			int indicador) {


		try {

			List<HistoricoNf> historico = new ArrayList<HistoricoNf>();

			String sql = "select oc_descricao desc_ocorrencia from stwopethisnf "
					     + "where cgc     = ?"
					     + "and serie     = ?"
					     + "and n_fiscal  = ?";
			
			//+ "and indicador = ?";

			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setString(1, cgc);
			stm.setString(2, serie);
			stm.setLong(3, n_fiscal);
			//stm.setInt(4, indicador);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				historico.add(populaHistorico(rs));
			}

			rs.close();
			stm.close();

			return historico;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private HistoricoNf populaHistorico(ResultSet rs) throws SQLException {
		
		HistoricoNf hisNf = new HistoricoNf();
			
		
		//hisNf.setCgc(rs.getString("cgc"));
		//hisNf.setSerie(rs.getString("serie"));
		//hisNf.setNotafiscal(rs.getInt("n_fiscal"));
	//	hisNf.setIndicador(rs.getInt("indicador"));
		hisNf.setDesc_ocorrencia(rs.getString("desc_ocorrencia"));
		

		
		return hisNf;
	}


	/*
	 * select his.oc_descricao desc_ocorrencia from stwopethisnf his where
	 * his.cgc =:cgc and his.serie =:serie and his.n_fiscal =:n_fiscal and
	 * his.indicador =:indicador
	 */

}
