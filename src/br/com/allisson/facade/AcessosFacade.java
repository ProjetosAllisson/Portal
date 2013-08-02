package br.com.allisson.facade;

import java.util.Calendar;

import br.com.allisson.dao.AcessosDAO;
import br.com.allisson.modelo.Acessos;
import br.com.allisson.modelo.User;

public class AcessosFacade {

	private AcessosDAO acessosDAO = new AcessosDAO();
	private Acessos acessos = new Acessos();
	
	public void createAcesso(User user){

		acessos.setLogin(user.getLogin());
						
		acessos.setDt_acesso(Calendar.getInstance());
		acessos.setUser(user);
		acessosDAO.beginTransaction();
		acessosDAO.save(acessos);
		acessosDAO.commitAndCloseTransaction();
	}
	
	
	
}
