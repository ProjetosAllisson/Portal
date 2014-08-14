package br.com.allisson.facade;

import br.com.allisson.dao.ContaEmailDAO;
import br.com.allisson.modelo.ContaEmail;

public class ContaEmailFacade {
	
	private static ContaEmailDAO contaEmailDao = new ContaEmailDAO();
	
	private static ContaEmail dadosContaEmail;
	
	private static ContaEmail findContaEmail(){
		
		contaEmailDao.beginTransaction();
		ContaEmail result = contaEmailDao.findConta();
		contaEmailDao.closeTransaction();
		
		return result;
		
		
	}
	
	public void createConta(ContaEmail conta){
		
		contaEmailDao.beginTransaction();
		contaEmailDao.save(conta);
		contaEmailDao.commitAndCloseTransaction();
		
		
	}
	
	public void updateConta(ContaEmail conta){
		contaEmailDao.beginTransaction();
		contaEmailDao.update(conta);
		contaEmailDao.commitAndCloseTransaction();
	}

	public static ContaEmail getDadosContaEmail() {
		
		if (dadosContaEmail==null){
			dadosContaEmail = findContaEmail();
		}
		
		return dadosContaEmail;
	}

	public static void setDadosContaEmail(ContaEmail dadosContaEmail) {
		ContaEmailFacade.dadosContaEmail = dadosContaEmail;
	}

}
