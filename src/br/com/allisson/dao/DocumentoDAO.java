package br.com.allisson.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.allisson.factory.ConnectionFactory;
import br.com.allisson.factory.ServicosDisponiveisFactory;
import br.com.allisson.modelo.Documento;
import br.com.allisson.modelo.ServicosDisponiveis;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;

public class DocumentoDAO {

	private final Connection connection;
	private final ServicosDisponiveis servicos;

	public DocumentoDAO() {
		try {
			this.connection = new ConnectionFactory().getConnection();
			this.servicos = new ServicosDisponiveisFactory().getServicos();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String selectDados() {

		return "select nf.documento||'-'||nf.fil_orig||'-'||cast(nf.nr_cto as integer)documento,"
				+ "    nf.documento doc, nf.fil_orig, cast(nf.nr_cto as integer) nr_cto,"
				+ "    nf.cgc, nf.serie, cast(nf.indicador as integer) indicador,"
				+ "       cast(nf.n_fiscal as integer) nota, nf.emissao, nf.entrega, remet.nome remetente, "
				+ "       dest.nome destinatario, mov.dt_manif embarque, man.dt_chegada chegada, rom.dt_saida,"

				+ "       case "
				+ "         when nf.entrega is not null then edi3.ocorrencia"
				+ "         when nf.dt_bx_entrega is not null then edi3.ocorrencia"
				+ "         when ent.dt_romaneio is not null then 'MERCADORIA EM ROTA DE ENTREGA'"
				+ "         when man.dt_chegada is not null then 'MERCADORIA EM DEPOSITO DESTINO'"
				+ "         when mov.dt_manif is not null then 'MERCADORIA EM TRANSFERENCIA'"
				+ "         when mov.dt_manif is null then 'MERCADORIA EM DEPOSITO ORIGEM'"
				+ "       end ocorrencia, "
				
				+ "       case "
                + "        when mov.nr_cto in(select img.nr_cto from stwopetdig img"
                + "            where img.documento = mov.documento"
                + "              and img.fil_orig  = mov.fil_orig"
                + "              and img.nr_cto    = mov.nr_cto) then 'SIM'"
//                + "              and img.nr_cto    = mov.nr_cto"
//                + "              and img.serie     = mov.serie) then 'SIM'"
                + "        else 'NAO'"
                + "       end imagem " 

				+ " from stwopetmov mov "

				+ "left join stwopetnota nf on(mov.documento = nf.documento and mov.fil_orig = nf.fil_orig "
				+ "                            and mov.nr_cto = nf.nr_cto)"
				// +
				// "                            and mov.nr_cto = nf.nr_cto and mov.serie = nf.serie_cto)"

				+ "left join stwopetcli remet on(nf.cgc = remet.cgc)"

				+ " left join stwcoltent ent on(ent.documento = mov.documento and ent.fil_orig = mov.fil_orig "
				//+
				// "                             and ent.nr_cto = mov.nr_cto and ent.serie = mov.serie)"
				+ "                             and ent.nr_cto = mov.nr_cto)"

				+ "left join stwopetman man on(man.nr_manif = mov.nr_manif)"
				+ "left join stwopetcli dest on(dest.cgc = mov.cgc_dest)"
				+ "left join stwopetedi1 edi1 on(edi1.cgc = nf.cgc)"
				+ "left join stwopetedi3 edi3 on(edi3.chv_ocorrencia = coalesce(edi1.chv_ocorrencia,'PROCEDA')"
				+ "                              and edi3.cod_ocorrencia = nf.cd_ocorrencia)"
				+ "left join stwcoltrom rom on(rom.fil_romaneio = ent.fil_romaneio and rom.romaneio = ent.romaneio)";

	}

	public List<Documento> listaDeDocumentos(String dataInicio,
			String dataTermino, String cliente) {
		try {

			List<Documento> documentos = new ArrayList<Documento>();

			String sql = selectDados() + "where nf.emissao between ? and ?"
					+ "  and mov.status <> 'CA'";

			if (cliente.equals("remet")) {
				sql = sql + " and mov.cgc_remet = ?";
			}

			if (cliente.equals("dest")) {
				sql = sql + " and mov.cgc_dest = ?";
			}

			if (cliente.equals("null")) {
				sql = sql + " and (mov.cgc_remet = ? or mov.cgc_dest = ?)";
			}

			// if (!cliente.equals("null")) {
			// sql = sql + " and mov.cgc_"+cliente;
			// }

			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setDate(1, new java.sql.Date(formataData(dataInicio + " 00:00")
					.getTime()));
			stm.setDate(2, new java.sql.Date(
					formataData(dataTermino + " 23:59").getTime()));

			// FacesContext ctx = FacesContext.getCurrentInstance();
			// HttpSession session = (HttpSession) ctx.getExternalContext()
			// .getSession(false);

			// stm.setString(3, "16.716.417/0001-95");

			User usuario = DevolveUsuarioSessao(); // (Usuario)
														// session.getAttribute("usuarioAutenticado");

			// if (!cliente.equals("null")) {
			if (cliente.equals("null")) {
				stm.setString(3, usuario.getCliente().getCgc());
				stm.setString(4, usuario.getCliente().getCgc());
			} else {
				stm.setString(3, usuario.getCliente().getCgc());
			}

		//	System.out.println(usuario.getCnpj());
			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				documentos.add(populaDocumento(rs));
			}

			rs.close();
			stm.close();

			return documentos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Documento> listaPorNotaFiscal(Integer notaFiscal) {

		try {

			List<Documento> documentos = new ArrayList<Documento>();

			String sql = selectDados() + "where nf.n_fiscal = ? "
					+ "  and mov.status <> 'CA'"
					+ " and (mov.cgc_remet = ? or mov.cgc_dest = ?)";

			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setInt(1, notaFiscal);

			User usuario = DevolveUsuarioSessao();

			stm.setString(2, usuario.getCliente().getCgc());
			stm.setString(3, usuario.getCliente().getCgc());

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				documentos.add(populaDocumento(rs));
			}

			rs.close();
			stm.close();

			return documentos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private Documento populaDocumento(ResultSet rs) throws SQLException {

		Documento documento = new Documento();
			
		documento.setDocumento(rs.getString("documento"));
		documento.setDoc(rs.getString("doc"));
		documento.setFil_orig(rs.getString("fil_orig"));
		documento.setNr_cto(rs.getInt("nr_cto"));
		documento.setCgc(rs.getString("cgc"));
		documento.setSerie(rs.getString("serie"));
		documento.setNota(rs.getLong("nota"));
		documento.setIndicador(rs.getInt("indicador"));

		documento.setEmissao(rs.getDate("emissao"));
		documento.setEmbarque(rs.getDate("embarque"));
		documento.setChegada(rs.getDate("chegada"));
		documento.setEntrega(rs.getDate("entrega"));
		documento.setSaida(rs.getDate("dt_saida"));

		documento.setRemetente(rs.getString("remetente"));
		documento.setDestinatario(rs.getString("destinatario"));

		documento.setOcorrencia(rs.getString("ocorrencia"));
		
		
				
		if (this.servicos.getComprovanteEntrega().equals(Criptografia.md5("SIM"))){
			documento.setTemImagem(rs.getString("imagem").equals("SIM"));
			System.out.println("Tem Imagem");
		}
		else
			documento.setTemImagem(false);
		
		
		// popula a data de finalizacao da tarefa, fazendo a conversao
		/*
		 * Date data = rs.getDate("dataFinalizacao"); if(data != null) {
		 * Calendar dataFinalizacao = Calendar.getInstance();
		 * dataFinalizacao.setTime(data);
		 * tarefa.setDataFinalizacao(dataFinalizacao); }
		 */

		// TODO Auto-generated method stub
		return documento;
	}

	private Date formataData(String data) {
		if (data == null) {
			return null;
		}

		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			date = (java.util.Date) formatter.parse(data);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public List<Documento> listaConsultaPublica(String cpf_cnpj) {
		try {

			List<Documento> documentos = new ArrayList<Documento>();

			String sql = selectDados() + "where mov.status <> 'CA'"
					+ " and (mov.cgc_remet = ? or mov.cgc_dest = ?)"
					+ " and (mov.dt_emissao >= current_timestamp -90)";

			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setString(1, cpf_cnpj);
			stm.setString(2, cpf_cnpj);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				documentos.add(populaDocumento(rs));
			}

			rs.close();
			stm.close();

			return documentos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private User DevolveUsuarioSessao() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) ctx.getExternalContext()
				.getSession(false);

		return (User) session.getAttribute("usuarioAutenticado");

	}
}
