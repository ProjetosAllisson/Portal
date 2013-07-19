package br.com.allisson.facade;

import java.util.Calendar;

import br.com.allisson.dao.AcessosDAO;
import br.com.allisson.modelo.Acessos;
import br.com.allisson.modelo.User;

public class AcessosFacade {

	private AcessosDAO acessosDAO = new AcessosDAO();
	
	public void createAcesso(User user){
		
		
		
		Acessos acessos = new Acessos();
		
		acessos.setLogin(user.getLogin());
		
		
		
		
		acessos.setDt_acesso(Calendar.getInstance());
		acessos.setUser(user);
		acessosDAO.beginTransaction();
		acessosDAO.save(acessos);
		acessosDAO.commitAndCloseTransaction();
	}
	
}
