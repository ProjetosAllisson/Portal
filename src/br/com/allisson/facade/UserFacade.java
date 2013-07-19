package br.com.allisson.facade;

import java.util.List;

import br.com.allisson.dao.UserDAO;
import br.com.allisson.modelo.Cliente;
import br.com.allisson.modelo.User;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String login, String senha){
		userDAO.beginTransaction();
		User user = userDAO.findUser(login);
		
		if (user == null || !user.getSenha().equals(senha)){
			return null;
		}
		else
			return user;
		
	}
	
	public void createUser(User user){
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commit();
	}
	
	public boolean isExists(String login){
		userDAO.beginTransaction();
		
		User user = userDAO.findUser(login);
		
		if (user == null){
			return false;
		}
		else
			return true;
		
	}
	
	public List<User> listAll(){
		userDAO.beginTransaction();
		List<User> result = userDAO.findAll();
		userDAO.closeTransaction();
		return result;
	}
	
	public void deleteUser(User user){
		
		System.out.println(user.getLogin());
		
		userDAO.beginTransaction();
		User persistedUser = userDAO.findReferenceOnly(user.getId());
		userDAO.delete(persistedUser);
		userDAO.commitAndCloseTransaction();
	
	}
	
	public void updateUser(User user){
		
		userDAO.beginTransaction();
		//User persistedUser = userDAO.find(user.getId());
		
		
		//Cliente cliente = new Cliente();
		//cliente.setCgc(user.getCliente().getCgc());
		
		//persistedUser.setAcesso_autorizado(user.getAcesso_autorizado());
		//persistedUser.setEmail(user.getEmail());
		//persistedUser.setCliente(cliente);
		userDAO.update(user);
		
		userDAO.commitAndCloseTransaction();
	}

}
