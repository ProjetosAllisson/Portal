package br.com.allisson.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.allisson.modelo.User;

public class UserDAO extends GenericDAO<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDAO() {
		super(User.class);
	}

	public User findUser(String login) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("login", login);
		//parameters.put("senha", senha);

		return super.findOneResult(User.FIND_USER_LOGIN, parameters);
	}
	
	
	
	public List<User> findAllUserNaoAutorizados(){
		return super.findAllResult(User.FIND_ALL_USER_NAO_AUTORIZADOS, null);
		
	}

}
