package br.com.allisson.facade;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.allisson.dao.ConfigSatwinDAO;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.ConfigSatwin;
import br.com.allisson.modelo.Duplicata;
import br.com.allisson.modelo.ServicosDisponiveis;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;
import br.com.allisson.util.Geral;

public class TesteHibernate {
	
	public static void main(String args[]){

		
		
		//Geral geral = new Geral();
		
		System.out.println(Geral.LimpaString("f.e-li/pe", "./"));
		
		
		/*
		//Duplicatas -----------------------------
		DuplicataFacade duplicataFacade = new DuplicataFacade();
		
		List<Duplicata> duplicatas = new ArrayList<Duplicata>();
		duplicatas = duplicataFacade.duplicatasEmAberto(); 
		
		for (Duplicata duplicata : duplicatas) {
			System.out.println(duplicata.getId().getTipo_doc());
			System.out.println(duplicata.getCliente().getNome());

			DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
			formatoDois.setMinimumFractionDigits(2);   
			formatoDois.setParseBigDecimal (true);  
			
			System.out.println(formatoDois.format(duplicata.getTot_fatura()));
		}
		//-------------------------
		*/
		
		//ConfigSatwinDAO configSatwinDAO = new ConfigSatwinDAO();
		//ConfigSatwin configSatwin = new ConfigSatwin();
		
		//configSatwinDAO.beginTransaction();
		//configSatwin = configSatwinDAO.configSatwin("ABASTECIMENTO", "Numero de casas decimais");
		
		//System.out.println(configSatwin.getTpo_clausula());
		
		ConfigSatwinFacade configSatwinFacade = new ConfigSatwinFacade();
		System.out.println(configSatwinFacade.leclausula("ABASTECIMENTO", "Numero de casas decimais", "SAO"));
		
		
		
		/*
		
		
		ClienteFacade clientefacade = new ClienteFacade();
		
		Cliente cliente = clientefacade.localiza("00.000.000/0000-04");
		
		System.out.println(cliente.getNome());
		
		
		
		
		
		
		User user = new User();
		
		Cliente cliente = new Cliente();
		
		user.setLogin("felipeho");
		user.setSenha("senha");
		//user.setCnpj("328.403.108-13");
//		user.setOperador("operador");
		
		
		cliente.setCgc("328.403.108-13");
		
		cliente.setUser(user);
		user.setCliente(cliente);
		
		UserFacade userFacade = new UserFacade();
		userFacade.createUser(user);
		
		
		
		
		
		/*
		
		
		Criptografia criptografia = new Criptografia();
		
		//System.out.println(criptografia.md5("SIM"));
		
		String crip;
		crip = criptografia.md5("SIM");
		
		//List<ServicosDisponiveis> servicos = new ArrayList<ServicosDisponiveis>();
		
		ServicosDisponiveis servicos = new ServicosDisponiveis();
		servicos.setComprovanteEntrega("NAO");
		servicos.setRastreamento("SIM");
		
		ServicosDisponiveisFacade servicosFacade = new ServicosDisponiveisFacade();
		servicosFacade.createServicos(servicos);
		
	
		//servicos = servicosFacade.servicos();
	
		
		//ServicosDisponiveis servico = new ServicosDisponiveis();
		
		//servico = servicosFacade.servico();
		//System.out.println(servico.getComprovanteEntrega());
		
		//for (ServicosDisponiveis elemento: servicos){
			//System.out.println(elemento.getComprovanteEntrega());
	//	}
	 * 
	 * 
	 */
		
	}

}
