package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.EmpresaDAO;
import br.com.allisson.dao.UserDAO;
import br.com.allisson.modelo.Empresa;
import br.com.allisson.modelo.Mensagem;
import br.com.allisson.modelo.User;
import br.com.allisson.util.EmailUtils;

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
		
		
		List<String> corpoEmail = new ArrayList<String>();
		
		
		if (!user.getEmail().equals("")) {
			
			
			EmpresaDAO empresaDao = new EmpresaDAO();
			Empresa empresa = new Empresa();
			
			empresa = empresaDao.DadosTransportadora();
			

			Mensagem msgCliente = new Mensagem();

			msgCliente.setTitulo("Portal de Servi�os Web. Cadastro Efetuado.");
			msgCliente.setDestino(user.getEmail());

			corpoEmail.clear();
			corpoEmail.add("Este � um e-mail autom�tico. N�o � necess�rio respond�-lo");
			corpoEmail.add("");
			corpoEmail.add("");

			corpoEmail.add("Seja bem vindo(a), "+user.getCliente().getNome());
			corpoEmail.add("");

			
			corpoEmail.add("Voc� efetuou com sucesso o seu cadastro!"); 
			
			corpoEmail.add("");
			
			corpoEmail.add("AGUARDE A LIBERA��O DE SEU ACESSO.");
			
			corpoEmail.add("");
			
			corpoEmail.add(empresa.getNome());

			msgCliente
					.setMensagens(corpoEmail);
			
				  
			try {
				// Executa aqui o codigo perigoso, que d� exce��o
				EmailUtils.enviarEmail(msgCliente);
			} catch (Exception e) {
				// Depois grava no contexto do Faces

			}
			
			
			
		}
		
		
		if (ContaEmailFacade.getDadosContaEmail()!=null){
			Mensagem msgAdmin = new Mensagem();
			
			msgAdmin.setTitulo("Conta criada com sucesso");
			msgAdmin.setDestino(ContaEmailFacade.getDadosContaEmail().getEmailadmin());
			
			
			corpoEmail.clear();
			corpoEmail.add("Ola,");
			corpoEmail.add("");
			corpoEmail.add("Cliente: "
					+ user.getCliente().getNome());
			corpoEmail.add("Login: "
					+ user.getLogin());
			corpoEmail.add("");
			corpoEmail.add("Efetuou cadastro no Portal Web. Necess�rio autoriza��o para Acesso.");
			
			msgAdmin.setMensagens(corpoEmail);
			
			
			try {
				// Executa aqui o codigo perigoso, que d� exce��o
				EmailUtils.enviarEmail(msgAdmin);
			} catch (Exception e) {
				// Depois grava no contexto do Faces

			}

		}
		
			
		
		
		
		
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
	
	public List<User> listAllNaoAutorizados() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllUserNaoAutorizados();
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
