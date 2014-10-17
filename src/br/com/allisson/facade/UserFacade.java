package br.com.allisson.facade;

import java.util.ArrayList;
import java.util.List;

import br.com.allisson.dao.EmpresaDAO;
import br.com.allisson.dao.UserDAO;
import br.com.allisson.modelo.Empresa;
import br.com.allisson.modelo.Mensagem;
import br.com.allisson.modelo.User;
import br.com.allisson.util.Criptografia;
import br.com.allisson.util.EmailUtils;
import br.com.allisson.util.Geral;

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

			msgCliente.setTitulo("Portal de Serviços Web. Cadastro Efetuado.");
			msgCliente.setDestino(user.getEmail());

			corpoEmail.clear();
			
			
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			corpoEmail.add("<div align='center'>");       
			
			String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
			
			corpoEmail.add("<img src="+imgHttp+">");  
			corpoEmail.add("</div>");  

			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<p>Prezado(a),"+user.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este é um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("Você efetuou com sucesso o seu cadastro em nosso Portal!");                   
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<strong>AGUARDE A LIBERAÇÃO DE SEU ACESSO.</strong><br/>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>**** FAVOR NÃO RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  
            
			msgCliente
					.setMensagens(corpoEmail);
			
				  
			try {
				// Executa aqui o codigo perigoso, que dá exceção
				EmailUtils.enviaEmailHtml(msgCliente);
			} catch (Exception e) {
				// Depois grava no contexto do Faces

			}
			
			
			
		}
		
		
		if (ContaEmailFacade.getDadosContaEmail()!=null){
			Mensagem msgAdmin = new Mensagem();
			
			msgAdmin.setTitulo("Conta criada com sucesso");
			msgAdmin.setDestino(ContaEmailFacade.getDadosContaEmail().getEmailadmin());
			
			corpoEmail.clear();
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<p>Prezado(a),</p>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<strong>Cliente: </strong>"+user.getCliente().getNome()+"<br/>");  
			corpoEmail.add("<strong>Login  : </strong>"+user.getLogin()+"<br/>");
			corpoEmail.add("</div>");
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Efetuou cadastro no Portal Web</center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>"); 
			
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Para autorizar agora mesmo. <a href="+Geral.getCaminhoURL()+"pages/public/autorizarAcessoURL.jsf?"+Criptografia.md5("idUser")+"="+Criptografia.criptografa(String.valueOf(user.getId()), 25)+">Autorizar</a></center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			
			      
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			msgAdmin.setMensagens(corpoEmail);
			
			
			try {
				// Executa aqui o codigo perigoso, que dá exceção
				EmailUtils.enviaEmailHtml(msgAdmin);
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
		List<User> result = userDAO.findAllUser();
		userDAO.closeTransaction();
		return result;
	}
	
	public List<User> listAllNaoAutorizados() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllUserNaoAutorizados();
		userDAO.closeTransaction();
		return result;
	}

	public User findUser(int id){
		userDAO.beginTransaction();
		User result = userDAO.find(id);
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
		userDAO.update(user);
		
		userDAO.commitAndCloseTransaction();
	}
	
	
	public void autorizaUsuario(User usuario){
		usuario.setAcesso_autorizado(true);
		this.updateUser(usuario);
		this.enviarEmailAcessoAutorizado(usuario);
	}
	
	
	public void enviarEmailAcessoAutorizado(User usuario) {

		EmpresaDAO empresaDao = new EmpresaDAO();
		Empresa empresa = new Empresa();

		empresa = empresaDao.DadosTransportadora();

		if (!usuario.getEmail().equals("")) {
			Mensagem msgAdmin = new Mensagem();

			msgAdmin.setTitulo("Acesso ao Portal Web");
			msgAdmin.setDestino(usuario.getEmail());

			List<String> corpoEmail = new ArrayList<String>();
			
			corpoEmail.clear();
			
			corpoEmail.add("<html>");               
			corpoEmail.add("<body  bgcolor='#EBEBEB'>");                    
			corpoEmail.add("<div align='center'>");                      
            
			String imgHttp = Geral.getCaminhoURL()+"img/logo.jpg";
			
			corpoEmail.add("<img src="+imgHttp+">");
			
			corpoEmail.add("</div>");  

			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("<p>Prezado(a),"+usuario.getCliente().getNome()+"</p>");  
			corpoEmail.add("<p>Este é um email de <strong>"+empresa.getNome()+"</strong>.</p>");
			corpoEmail.add("</div>");  
            
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>");  
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");
			corpoEmail.add("Informamos que seu usuário: " + usuario.getLogin()
					+ " foi autorizado a acessar nosso Portal Web");
			corpoEmail.add("</div>");  
			                   
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>Para acessar agora mesmo <a href="+Geral.getCaminhoURL()+">Acesse</a></center></b>");   
			corpoEmail.add("</div>");  
			
			corpoEmail.add("<br/>");  
			corpoEmail.add("<br/>"); 
			
			
			corpoEmail.add("<div colspan='1' height='30' align='center'>");  
			corpoEmail.add("<b><center>**** FAVOR NÃO RETORNAR ESTE EMAIL ****</center></b>");   
			corpoEmail.add("</div>");  
                  
			corpoEmail.add("</body>");  
			corpoEmail.add("</html>");  

			
			msgAdmin.setMensagens(corpoEmail);

			EmailUtils.enviaEmailHtml(msgAdmin);

			
		}

	}
	
	
	

}
