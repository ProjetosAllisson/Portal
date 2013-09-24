package br.com.allisson.facade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.DuplicataDAO;
import br.com.allisson.modelo.Duplicata;
import br.com.allisson.util.Geral;

public class DuplicataFacade {

	private DuplicataDAO duplicataDAO = new DuplicataDAO();
	private ConfigSatwinFacade configSatwinfacade = new ConfigSatwinFacade();
	private List<Duplicata> result = new ArrayList<Duplicata>();

	private void percorreDuplicatas() {
		for (Duplicata duplicata : result) {

			String caminho = configSatwinfacade.leclausula("FATURAMENTO",
					"Path para Salvar Boletos em PDF", duplicata.getId()
							.getFil_orig());

			String arquivo = caminho
					+ '/'
					+ Geral.LimpaString(duplicata.getCliente().getCgc(),
							"[./-]")
					+ String.format("%06d", duplicata.getId().getFatura())
					+ ".pdf";

			File file = new File(arquivo);

			if (file.exists()) {
				duplicata.setBoletoGerado(true);
			} else {
				duplicata.setBoletoGerado(false);
			}

		}

	}

	public List<Duplicata> duplicatasEmAberto() {
		duplicataDAO.beginTransaction();

		result = duplicataDAO.duplicatasEmAberto();

		duplicataDAO.closeTransaction();

		this.percorreDuplicatas();
		
		return result;
	}
	
	public List<Duplicata> duplicatasEmAbertoPorGrupoCliente(){
		duplicataDAO.beginTransaction();
		
		result = duplicataDAO.duplicatasEmAbertoPorGrupoCliente();
		
		duplicataDAO.closeTransaction();
		
		this.percorreDuplicatas();
		
		return result;
	}

}
